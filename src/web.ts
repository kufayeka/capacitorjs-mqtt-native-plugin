import { WebPlugin } from '@capacitor/core';

import type { MqttBridgePlugin } from './definitions';

// Define the MqttBridgeWeb class that implements the MqttBridgePlugin interface
export class MqttBridgeWeb extends WebPlugin implements MqttBridgePlugin {
  // Implement the `connect` method from the `MqttBridgePlugin` interface
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
  }): Promise<any> {
    // Log the `options` parameter
    console.log(options);
    // Throw an error indicating that this method is not implemented
    throw new Error('Method not implemented.');
  }

  // Implement the `disconnect` method from the `MqttBridgePlugin` interface
  disconnect(): Promise<any> {
    // Throw an error indicating that this method is not implemented
    throw new Error('Method not implemented.');
  }

  // Implement the `subscribe` method from the `MqttBridgePlugin` interface
  subscribe(options: {
    topic: string;
    qos: number;
  }): Promise<{ topic: string; qos: number }> {
    // Log the `options` parameter
    console.log(options);
    // Throw an error indicating that this method is not implemented
    throw new Error('Method not implemented.');
  }

  // Implement the `publish` method from the `MqttBridgePlugin` interface
  publish(options: {
    topic: string;
    payload: string;
    qos: number;
    retained: boolean;
  }): Promise<{
    topic: string;
    payload: string;
    qos: number;
    retained: boolean;
    messageId: any;
  }> {
    // Log the `options` parameter
    console.log(options);
    // Throw an error indicating that this method is not implemented
    throw new Error('Method not implemented.');
  }
}
