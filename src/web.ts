import { WebPlugin } from '@capacitor/core';

import type { MqttBridgePlugin } from './definitions';

export class MqttBridgeWeb extends WebPlugin implements MqttBridgePlugin {
  async mqttSubscribe(mqttSub: { topic: string; qos: number }): Promise<any> {
    console.log(mqttSub);
    throw new Error('Method not implemented.');
  }

  async mqttPublish(mqttPub: {
    topic: string;
    payload: string;
    qos: number;
    retained: boolean;
  }): Promise<any> {
    console.log(mqttPub);
    throw new Error('Method not implemented.');
  }

  mqttConnect(mqttOpt: { serverURI: string; port: number }): void {
    console.log(mqttOpt);
    throw new Error('Method not implemented.');
  }

  mqttDisconnect(): void {
    throw new Error('Method not implemented.');
  }
}
