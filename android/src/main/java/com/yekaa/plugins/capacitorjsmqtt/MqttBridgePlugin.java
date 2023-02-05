package com.yekaa.plugins.capacitorjsmqtt;

import static org.eclipse.paho.client.mqttv3.MqttConnectOptions.MQTT_VERSION_3_1_1;

import android.util.Log;
import android.widget.Toast;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;


@CapacitorPlugin(name = "MqttBridge")
public class MqttBridgePlugin extends Plugin {

    private MqttBridge implementation;

    MqttAndroidClient client;
    MqttConnectOptions connectOptions = new MqttConnectOptions();

    @Override
    public void load() {
        super.load();
        implementation = new MqttBridge(getContext());

        Log.d("MQTT", "On LOAD");
    }

    @Override
    protected void handleOnResume() {
        super.handleOnResume();
        Log.d("MQTT", "On RESUME");
    }

    @PluginMethod
    public void mqttConnect(PluginCall call) throws JSONException {
        JSObject dataFromPluginCall = call.getData();

        String serverURI = dataFromPluginCall.getString("serverURI") + ":" + dataFromPluginCall.getString("port");

        Log.d("MQTT",  serverURI);

        mqttConnect(serverURI);

        call.resolve();
    }

    @PluginMethod
    public void mqttDisconnect(PluginCall call) {

        Log.d("MQTT",  "DC");

        mqttDisconnect();

        call.resolve();
    }

    @PluginMethod
    public void mqttSubscribe(PluginCall call) {

        JSObject dataFromPluginCall = call.getData();
        Log.d("MQTT",  dataFromPluginCall.toString());

        String topic = dataFromPluginCall.getString("topic");
        Integer qos = dataFromPluginCall.getInteger("qos");

        try {
            this.client.subscribe(topic, qos);
        } catch (MqttException e) {
            e.printStackTrace();
        }

        call.resolve();
    }

    @PluginMethod
    public void mqttPublish(PluginCall call) throws UnsupportedEncodingException, MqttException {

        JSObject dataFromPluginCall = call.getData();
        Log.d("MQTT",  dataFromPluginCall.toString());

        String topic = dataFromPluginCall.getString("topic");
        String msg = dataFromPluginCall.getString("payload");
        Integer qos = dataFromPluginCall.getInteger("qos");
        Boolean retain = dataFromPluginCall.getBool("retained");

        byte[] encodedPayload;
        encodedPayload = msg.getBytes("UTF-8");

        MqttMessage payload = new MqttMessage(encodedPayload);
        payload.setQos(qos);
        payload.setRetained(retain);

        this.client.publish(topic, payload);

        call.resolve();
    }

    /**********************************************************************************/

    @PluginMethod
    public void notifyMqttConnectionListener(boolean isConnected){
        JSObject jsObject = new JSObject();
        jsObject.put("connected", isConnected);

        notifyListeners("mqttConnection", jsObject);
    }

    @PluginMethod
    public void notifyMqttMessageArrivedListener(JSObject val){
        JSObject jsObject = new JSObject();
        jsObject.put("message", val);

        notifyListeners("messageArrived", jsObject);
    }

    /**********************************************************************************/

    public void mqttConnect(String serverURI) {
        String clientId = MqttClient.generateClientId();
        this.client = new MqttAndroidClient(getActivity(), serverURI, clientId);
//        this.connectOptions.setUserName();
//        this.connectOptions.setPassword();
        this.connectOptions.setMqttVersion(MQTT_VERSION_3_1_1);
        this.connectOptions.setAutomaticReconnect(false);
        this.connectOptions.setConnectionTimeout(4);
        this.connectOptions.setKeepAliveInterval(60);

        try {
            IMqttToken token = this.client.connect(this.connectOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getActivity(), "MQTT CONNECTED", Toast.LENGTH_SHORT).show();
                    notifyMqttConnectionListener(true);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getActivity(), "MQTT CONNECT FAILED", Toast.LENGTH_SHORT).show();
                    notifyMqttConnectionListener(false);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

        this.client.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("MQTT", "CONNECT COMPLETE");
            }

            @Override
            public void connectionLost(Throwable cause) {
                Log.d("MQTT", "CONNECTION LOST");

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("MQTT", topic + message);

                JSObject jsObject = new JSObject();
                jsObject.put("topic", topic);
                jsObject.put("message", message);

                notifyMqttMessageArrivedListener(jsObject);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    public void mqttDisconnect(){
        try {
            this.client.close();
            IMqttToken token = this.client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(getActivity(), "MQTT DISCONNECTED", Toast.LENGTH_SHORT).show();
                    notifyMqttConnectionListener(false);
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(getActivity(), "MQTT DISCONNECT FAILED", Toast.LENGTH_SHORT).show();
                    notifyMqttConnectionListener(true);
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}