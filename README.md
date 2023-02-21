# CapacitorJS MQTT Native Plugin

⚡️This is an easy-to-use CapacitorJS plugin that enables your CapacitorJS-powered Android mobile app to connect to an MQTT broker and send/receive messages. With this plugin, you can easily implement MQTT-based communication in your Android CapacitorJS app natively.

### Support for iOS is currently in development and will be added soon.

#

## Installation

```bash
npm install capacitorjs-mqtt-bridge
npx cap sync
```

#

## Examples

### Connect to an MQTT Broker :

To connect to an MQTT broker, you can use the `connect()` method provided by the plugin. The following code demonstrates how to connect to an MQTT broker:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

// Set the MQTT server connection options
const connectOptions = {
  serverURI: 'tcp://broker.hivemq.com',
  port: 1883,
  clientId: '',
  username: 'your_mqtt_broker_username',
  password: 'your_mqtt_broker_password',
  setCleanSession: true,
  connectionTimeout: 30,
  keepAliveInterval: 60,
  setAutomaticReconnect: true,
  // optional:
  // setLastWill: {
  // willTopic: null, //string
  // willPayload: null, //string
  // willQoS: null, //number
  // setRetained: null, //boolean
  // }
};

MqttBridge.connect(connectOptions)
  .then(() => {
    console.log('Connect Success');
  })
  .catch((error: any) => {
    console.log('Connect Failed:', error);
  });
```

### Disconnecting from the MQTT Broker :

To disconnect from the MQTT broker, you can use the `disconnect()` method provided by the plugin. The following code demonstrates how to disconnect from an MQTT broker:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

MqttBridge.disconnect()
  .then(() => {
    console.log('Disconnect Success');
  })
  .catch((error: any) => {
    console.log('Disconnect Failed:', error);
  });
```

### Subscribing to an MQTT Topic :

To subscribe to an MQTT topic, you can use the `subscribe()` method provided by the plugin. The following code demonstrates how to subscribe to an MQTT topic:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

MqttBridge.subscribe({ topic: 'your_mqtt_topic', qos: 0 })
  .then((result: any) => {
    console.log('Subscribe Success:', result.topic, result.qos);
  })
  .catch((error: any) => {
    console.log('Subscribe Failed:', error);
  });
```

### Publishing a Message to an MQTT Topic :

To publish a message to an MQTT topic, you can use the `publish()` method provided by the plugin. The following code demonstrates how to publish a message to an MQTT topic:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

MqttBridge.publish({
  topic: 'your_mqtt_topic',
  payload: 'your_mqtt_message',
  qos: 0,
  retained: false,
})
  .then((result: any) => {
    console.log(
      'Publish Success:',
      result.topic,
      result.qos,
      result.payload,
      result.retained,
      result.messageId,
    );
  })
  .catch((error: any) => {
    console.log('Publish Failed:', error);
  });
```

### Listen to Incoming Messages :

To listen to incoming messages, you can add a CapacitorJS listener with this event name : `onMessageArrived`. The following code demonstrates how to publish a message to an MQTT topic:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

MqttBridge.addListener('onMessageArrived', (result: any) => {
  console.log('onMessageArrived:', result.topic, result.message);
});
```

### Listen to ConnectComplete Callback :

This event only triggered when the connection to the server is completed successfully. It also triggered when the client was reconnected after a connection lost happened. To implement this, You can add a CapacitorJS listener with this event name : `onConnectComplete`. The following code demonstrates how to listen to ConnectComplete Callback:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

MqttBridge.addListener('onConnectComplete', (result: any) => {
  console.log('onConnectComplete:', result.reconnected, result.serverURI);
});
```

### Listen to ConnectionLost Callback :

This event only triggered when the connection to the server is lost. To implement this, You can add a CapacitorJS listener with this event name : `onConnectionLost`. The following code demonstrates how to listen to ConnectionLost Callback:

```typescript
import { MqttBridge } from '@yekaa/capacitorjs-mqtt-native-plugin';

MqttBridge.addListener(
  'onConnectionLost',
  (result: { connectionStatus: any; reasonCode: any; message: any }) => {
    console.log(
      'onConnectionLost:',
      result.connectionStatus,
      result.reasonCode,
      result.message,
    );
  },
);
```

#

## API

<docgen-index>

- [`connect(...)`](#connect)
- [`disconnect()`](#disconnect)
- [`subscribe(...)`](#subscribe)
- [`publish(...)`](#publish)
- [`addListener('onConnectionLost', ...)`](#addlisteneronconnectionlost)
- [`addListener('onConnectComplete', ...)`](#addlisteneronconnectcomplete)
- [`addListener('onMessageArrived', ...)`](#addlisteneronmessagearrived)
- [Interfaces](#interfaces)
- [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### connect(...)

```typescript
connect(options: { serverURI: string; port: number; clientId: string; username: string; password: string; setCleanSession: boolean; connectionTimeout: number; keepAliveInterval: number; setAutomaticReconnect: boolean; setLastWill?: { willTopic: string; willPayload: string; willQoS: number; setRetained: boolean; }; }) => Promise<any>
```

| Param         | Type                                                                                                                                                                                                                                                                                                                      |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ serverURI: string; port: number; clientId: string; username: string; password: string; setCleanSession: boolean; connectionTimeout: number; keepAliveInterval: number; setAutomaticReconnect: boolean; setLastWill?: { willTopic: string; willPayload: string; willQoS: number; setRetained: boolean; }; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

---

### disconnect()

```typescript
disconnect() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

---

### subscribe(...)

```typescript
subscribe(options: { topic: string; qos: number; }) => Promise<{ topic: string; qos: number; }>
```

| Param         | Type                                         |
| ------------- | -------------------------------------------- |
| **`options`** | <code>{ topic: string; qos: number; }</code> |

**Returns:** <code>Promise&lt;{ topic: string; qos: number; }&gt;</code>

---

### publish(...)

```typescript
publish(options: { topic: string; payload: string; qos: number; retained: boolean; }) => Promise<{ topic: string; payload: string; qos: number; retained: boolean; messageId: any; }>
```

| Param         | Type                                                                             |
| ------------- | -------------------------------------------------------------------------------- |
| **`options`** | <code>{ topic: string; payload: string; qos: number; retained: boolean; }</code> |

**Returns:** <code>Promise&lt;{ topic: string; payload: string; qos: number; retained: boolean; messageId: any; }&gt;</code>

---

### addListener('onConnectionLost', ...)

```typescript
addListener(eventName: 'onConnectionLost', listener: onConnectionLostListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                          |
| --------------- | ----------------------------------------------------------------------------- |
| **`eventName`** | <code>'onConnectionLost'</code>                                               |
| **`listener`**  | <code><a href="#onconnectionlostlistener">onConnectionLostListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

---

### addListener('onConnectComplete', ...)

```typescript
addListener(eventName: 'onConnectComplete', listener: onConnectCompleteListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                            |
| --------------- | ------------------------------------------------------------------------------- |
| **`eventName`** | <code>'onConnectComplete'</code>                                                |
| **`listener`**  | <code><a href="#onconnectcompletelistener">onConnectCompleteListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

---

### addListener('onMessageArrived', ...)

```typescript
addListener(eventName: 'onMessageArrived', listener: onMessageArrivedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                          |
| --------------- | ----------------------------------------------------------------------------- |
| **`eventName`** | <code>'onMessageArrived'</code>                                               |
| **`listener`**  | <code><a href="#onmessagearrivedlistener">onMessageArrivedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

---

### Interfaces

#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |

### Type Aliases

#### onConnectionLostListener

<code>(x: { connectionStatus: string; reasonCode: number; message: string; }): void</code>

#### onConnectCompleteListener

<code>(x: { reconnected: boolean; serverURI: string; }): void</code>

#### onMessageArrivedListener

<code>(x: { topic: string; message: string; }): void</code>

</docgen-api>
