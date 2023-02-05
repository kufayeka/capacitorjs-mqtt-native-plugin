import { registerPlugin } from '@capacitor/core';

import type { MqttBridgePlugin } from './definitions';

const MqttBridge = registerPlugin<MqttBridgePlugin>('MqttBridge', {
  android: () => import('./web').then(m => new m.MqttBridgeWeb()),
});

export * from './definitions';
export { MqttBridge };
