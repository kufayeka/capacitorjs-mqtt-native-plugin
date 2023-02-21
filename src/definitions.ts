import type { PluginListenerHandle } from '@capacitor/core';

export type onConnectionLostListener = (x: {
  connectionStatus: string;
  reasonCode: number;
  message: string;
}) => void;

export type onConnectCompleteListener = (x: {
  reconnected: boolean;
  serverURI: string;
}) => void;

export interface MqttBridgePlugin {
  connect(options: {
    serverURI: string;
    port: number;
    clientId: string;
    username: string;
    password: string;
    setCleanSession: boolean;
    connectionTimeout: number;
    keepAliveInterval: number;
    setAutomaticReconnect: boolean;
    setLastWill?: {
      willTopic: string;
      willPayload: string;
      willQoS: number;
      setRetained: boolean;
    };
  }): Promise<any>;

  disconnect(): Promise<any>;

  subscribe(options: { topic: string; qos: number }): Promise<any>;

  publish(options: {
    topic: string;
    qos: number;
    retained: boolean;
  }): Promise<any>;

  addListener(
    eventName: 'onConnectionLost',
    listener: onConnectionLostListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;

  addListener(
    eventName: 'onConnectComplete',
    listener: onConnectCompleteListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}
