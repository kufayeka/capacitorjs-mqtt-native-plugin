import { WebPlugin } from '@capacitor/core';

import type { MqttBridgePlugin } from './definitions';

export class MqttBridgeWeb extends WebPlugin implements MqttBridgePlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
