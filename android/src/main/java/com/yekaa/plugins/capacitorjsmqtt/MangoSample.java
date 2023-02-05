package com.yekaa.plugins.capacitorjsmqtt;

import android.util.Log;

public interface MangoSample {
    default void one(){
        Log.d("MQTT",  "mango one");
        two("mango two");
    }

    default void two(String s){}
}
