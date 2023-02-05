package com.yekaa.plugins.capacitorjsmqtt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.getcapacitor.Bridge;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginHandle;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;


public class MqttBridge extends Plugin implements MangoSample {

    private final Context context;
    MqttAndroidClient client;
    MqttConnectOptions connectOptions = new MqttConnectOptions();
    MqttBridgePlugin mqttBridgePlugin = new MqttBridgePlugin();
    byte[] encodedPayload = new byte[0];
    public static final String clientId = MqttClient.generateClientId();

    public MqttBridge(Context context) {
        this.context = context;
    }

    public void mqttConnect(String serverURI) {
        String clientId = MqttClient.generateClientId();
        this.client = new MqttAndroidClient(context, serverURI, clientId);
//        this.connectOptions.setUserName();
//        this.connectOptions.setPassword();
        this.connectOptions.setAutomaticReconnect(false);
        this.connectOptions.setConnectionTimeout(4);
        this.connectOptions.setKeepAliveInterval(60);

        try {
            IMqttToken token = this.client.connect(this.connectOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(context, "MQTT CONNECTED", Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, "MQTT CONNECT FAILED", Toast.LENGTH_SHORT).show();

                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void mqttDisconnect() {
        try {
            IMqttToken token = this.client.disconnect();
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Toast.makeText(context, "MQTT DISCONNECTED", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Toast.makeText(context, "MQTT DISCONNECT FAILED", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }

}
