/* eslint-disable @typescript-eslint/no-unused-vars */
import { WebPlugin } from '@capacitor/core';

import type { MqttBridgePlugin } from './definitions';

export class MqttBridgeWeb extends WebPlugin implements MqttBridgePlugin {
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
    console.log(options);
    throw new Error('Method not implemented.');
  }
  disconnect(): Promise<any> {
    throw new Error('Method not implemented.');
  }
  subscribe(options: { topic: string; qos: number }): Promise<any> {
    console.log(options);
    throw new Error('Method not implemented.');
  }
  publish(options: {
    topic: string;
    qos: number;
    retained: boolean;
  }): Promise<any> {
    console.log(options);
    throw new Error('Method not implemented.');
  }
}
