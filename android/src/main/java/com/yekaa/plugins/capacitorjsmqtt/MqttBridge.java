package com.yekaa.plugins.capacitorjsmqtt;

import com.yekaa.plugins.capacitorjsmqtt.Constants;

import android.content.Context;
import android.util.Log;

import com.getcapacitor.JSObject;
import com.getcapacitor.PluginCall;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONException;

import java.nio.charset.StandardCharsets;

public class MqttBridge implements MqttCallbackExtended {

    // A constant string used as a tag for logging
    private static final String TAG = "MqttBridge";

    // A constant integer value representing the Quality of Service (QoS) for MQTT messages
    private static final int QOS = 1;

    // An instance of the MqttBridgePlugin class
    private MqttBridgePlugin pluginInstance;

    // An instance of the MqttAndroidClient class, which represents an MQTT client for Android
    private MqttAndroidClient mqttClient;

    // A Context object representing the context in which the MQTT bridge is running
    private Context context;

    // A string representing the URI of the MQTT server to connect to
    private String serverURI;

    // A string representing the ID of the MQTT client
    private String clientId;

    // A string representing the port number to connect to on the MQTT server
    private String port;

    // A string representing the username to use for authentication when connecting to the MQTT server
    private String username;

    // A string representing the password to use for authentication when connecting to the MQTT server
    private String password;

    // A boolean flag indicating whether the MQTT client is currently connecting to the server
    private boolean isConnecting = false;

    /**
     * Constructor for the MqttBridge class
     *
     * @param context the context in which the MQTT bridge is running
     * @param pluginInstance an instance of the MqttBridgePlugin class
     */
    public MqttBridge(Context context, MqttBridgePlugin pluginInstance) {
        this.context = context;
        this.pluginInstance = pluginInstance;
    }


    public void connect(final PluginCall call) {
        // Extract necessary information from the PluginCall data
        JSObject dataFromPluginCall = call.getData();
        String serverURI = dataFromPluginCall.getString("serverURI");
        int port = dataFromPluginCall.getInteger("port");
        String clientId = dataFromPluginCall.getString("clientId");
        String username = dataFromPluginCall.getString("username");
        String password = dataFromPluginCall.getString("password");

        // Extract optional information from the PluginCall data with default values
        boolean setCleanSession = dataFromPluginCall.getBoolean("setCleanSession", false);
        int connectionTimeout = dataFromPluginCall.getInteger("connectionTimeout", 30);
        int keepAliveInterval = dataFromPluginCall.getInteger("keepAliveInterval", 60);
        boolean setAutomaticReconnect = dataFromPluginCall.getBoolean("setAutomaticReconnect", true);

        // Validate the required fields
        if (serverURI == null || serverURI.isEmpty()) {
            call.reject("serverURI is required");
            return;
        }
        if (clientId == null || clientId.isEmpty()) {
            call.reject("clientId is required");
            return;
        }
        if (username == null || username.isEmpty()) {
            call.reject("username is required");
            return;
        }
        if (password == null || password.isEmpty()) {
            call.reject("password is required");
            return;
        }

        // Construct the full server URI
        String fullURI = serverURI + ":" + port;

        // Create and configure the MqttConnectOptions object
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setCleanSession(setCleanSession);
        mqttConnectOptions.setConnectionTimeout(connectionTimeout);
        mqttConnectOptions.setKeepAliveInterval(keepAliveInterval);
        mqttConnectOptions.setAutomaticReconnect(setAutomaticReconnect);
        mqttConnectOptions.setUserName(username);
        mqttConnectOptions.setPassword(password.toCharArray());

        // Set the last will message if it exists
        JSObject lastWillObj = dataFromPluginCall.getJSObject("setLastWill");
        if (lastWillObj != null) {
            String willTopic = lastWillObj.getString("willTopic");
            String willPayload = lastWillObj.getString("willPayload");
            int willQoS = lastWillObj.getInteger("willQoS");
            boolean setRetained = lastWillObj.getBoolean("setRetained", false);

            mqttConnectOptions.setWill(willTopic, willPayload.getBytes(StandardCharsets.UTF_8), willQoS, setRetained);
        }

        // Check if the client is not already connecting and the client is not already connected
        if (!isConnecting && (mqttClient == null || !mqttClient.isConnected())) {
            // Set isConnecting to true to avoid multiple connect requests
            isConnecting = true;
            // Create the MqttAndroidClient object
            mqttClient = new MqttAndroidClient(context, fullURI, clientId);
            // Set this as the callback for the MQTT client
            mqttClient.setCallback(this);

            try {
                // Attempt to connect to the MQTT broker using the provided options
                mqttClient.connect(mqttConnectOptions, context, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        // Set isConnecting to false to allow future connect requests
                        isConnecting = false;
                        // Resolve the PluginCall to signal successful connection
                        call.resolve();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        // set isConnecting to false to indicate that the connection attempt has failed
                        isConnecting = false;

                        // Get the error message from the Throwable object
                        String errorMessage = exception.getMessage();

                        // Reject the plugin call with an error message containing the error message obtained from the Throwable object
                        call.reject("Failed to connect to MQTT broker: " + errorMessage);
                    }
                });
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect(final PluginCall call){
        if(mqttClient.isConnected()){
            try {
                mqttClient.disconnect();

                call.resolve();
            } catch (MqttException e) {
                call.reject("Disconnect Failed: " + e);
                e.printStackTrace();
            }
        }
    }

    public void subscribe(final PluginCall call) {

        // Check if MQTT client is connected
        if (mqttClient == null || !mqttClient.isConnected()) {
            call.reject("MQTT client is not connected");
            return;
        }

        // Get topic and qos from the plugin call
        final String topic = call.getString("topic");
        final int qos = call.getInt("qos", 0);

        try {
            // Subscribe to the MQTT topic with the given qos
            mqttClient.subscribe(topic, qos, null, new IMqttActionListener() {

                // This method is called when the subscription is successful
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {

                    // Create a JSObject to return the subscribed topic and qos
                    JSObject data = new JSObject();
                    data.put("topic", topic);
                    data.put("qos", qos);

                    // Resolve the plugin call with the data object
                    call.resolve(data);
                }

                // This method is called when the subscription fails
                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                    // Reject the plugin call with an error message
                    call.reject("Failed to subscribe to topic: " + topic);
                }
            });
        } catch (MqttException ex) {

            // Reject the plugin call with an error message
            call.reject("Failed to subscribe to topic: " + topic);
        }
    }

    public void publish(final PluginCall call) {
        // Check if the MQTT client is not connected
        if (mqttClient == null || !mqttClient.isConnected()) {
            call.reject("MQTT client is not connected");
            return;
        }

        // Obtain the topic, qos, retained, and payload from the PluginCall object
        final String topic = call.getString("topic");
        final int qos = call.getInt("qos", 0);
        final boolean retained = call.getBoolean("retained", false);
        final String payload = call.getString("payload");

        try {
            // Create an MqttMessage object with the payload
            MqttMessage message = new MqttMessage(payload.getBytes());
            // Set the qos and retained flag of the message
            message.setQos(qos);
            message.setRetained(retained);

            // Publish the message to the topic using the mqttClient object
            mqttClient.publish(topic, message, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // Construct a JSObject with the topic, qos, retained, payload, and messageId
                    JSObject data = new JSObject();
                    data.put("topic", topic);
                    data.put("qos", qos);
                    data.put("retained", retained);
                    data.put("payload", payload);
                    data.put("messageId", asyncActionToken.getMessageId());
                    // Resolve the PluginCall with the JSObject
                    call.resolve(data);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Reject the PluginCall with an error message
                    call.reject("Failed to publish message to topic: " + topic);
                }
            });
        } catch (MqttException ex) {
            // Reject the PluginCall with an error message
            call.reject("Failed to publish message to topic: " + topic);
        }
    }


    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        JSObject data = new JSObject();
        data.put("reconnected", reconnect);
        data.put("serverURI", serverURI);

        pluginInstance.handleCallback(Constants.CONNECT_COMPLETE_EVENT_NAME, data);
    }

    @Override
    public void connectionLost(Throwable cause) {
        // Create a JSObject to hold the connection lost data
        JSObject data = new JSObject();
        String message = "Client disconnected ";
        int reasonCode = -1;

        if (cause == null) {
            // If the cause is null, then the client disconnected purposefully
            message += "purposefully";
            reasonCode = MqttException.REASON_CODE_CLIENT_DISCONNECTING;
        } else {
            // If the cause is not null, then the client disconnected unexpectedly
            MqttException mqttException = (MqttException) cause;
            reasonCode = mqttException.getReasonCode();
            if (reasonCode == MqttException.REASON_CODE_CONNECTION_LOST) {
                message += "unexpectedly";
            } else if (reasonCode == MqttException.REASON_CODE_CLIENT_DISCONNECTING) {
                message += "purposefully";
            }
        }

        // Add the connection lost data to the JSObject
        data.put("connectionStatus", "disconnected");
        data.put("reasonCode", reasonCode);
        data.put("message", message);

        // Call the handleCallback method of the pluginInstance with the connection lost data
        pluginInstance.handleCallback(Constants.CONNECTION_LOST_EVENT_NAME, data);

        // Print the message to the log for debugging purposes
        Log.d("MQTT", message);
    }


    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // Create a JSObject to hold the message data
        JSObject data = new JSObject();
        data.put("topic", topic);
        data.put("message", message.toString());

        // Call the handleCallback method of the pluginInstance with the message data
        pluginInstance.handleCallback(Constants.MESSAGE_ARRIVED_EVENT_NAME, data);

        // Print the message to the log for debugging purposes
        Log.d("MQTT", "Message arrived on topic " + topic + ": " + message.toString());
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {


    }
}
