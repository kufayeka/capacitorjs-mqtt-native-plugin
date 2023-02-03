package com.yekaa.plugins.capacitorjsmqtt;

import android.util.Log;

public class MqttBridge {

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
