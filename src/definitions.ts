export interface MqttBridgePlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
