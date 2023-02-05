import type { PluginListenerHandle } from '@capacitor/core';

export type mqttConnectionListener = (x: any) => void;
export type messageArrivedListener = (x: any) => void;

export interface MqttBridgePlugin {
  mqttConnect(mqttOpt: { serverURI: string; port: number }): void;

  mqttDisconnect(): void;

  mqttPublish(mqttPub: {
    topic: string;
    payload: string;
    qos: number;
    retained: boolean;
  }): void;

  mqttSubscribe(mqttSub: { topic: string; qos: number }): void;

  addListener(
    eventName: 'mqttConnection',
    listener: mqttConnectionListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  addListener(
    eventName: 'messageArrived',
    listener: messageArrivedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}
