# CapacitorJS MQTT Native Plugin

⚡️ This plugin enables CapacitorJS-powered Android mobile apps to connect to an MQTT broker and send/receive messages natively.

#### ⚠️ Note: Supports only for android for now.

## Installation

To install the plugin, run:

```bash
npm install capacitorjs-mqtt-bridge
npx cap sync
```

## Examples

Here are some examples of how to use the plugin in your capacitorJS project using Typescript:

- [`Connect to an MQTT Broker`](#connect-to-broker)
- [`Disconnecting from the MQTT Broker`](#disconnect-from-broker)
- [`Subscribing to an MQTT Topic`](#subscribe-to-topic)
- [`Publishing a Message to an MQTT Topic`](#publish-to-topic)
- [`Listen to Incoming Messages`](#message-arrived-event)
- [`Listen to ConnectComplete Event`](#connect-complete-event)
- [`Listen to ConnectionLost Event`](#listen-to-connectionlost-event)

<a name="connect-to-broker"></a>

### Connect to an MQTT Broker :

To connect to an MQTT broker, you can use the `connect()` method provided by the plugin. The following code demonstrates how to connect to an MQTT broker:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Set the MQTT server connection options
const connectionOptions = {
  serverURI: 'tcp://', // MQTT broker URI
  port: 1883, // MQTT broker port
  clientId: '', // client ID for connection
  username: 'your_mqtt_broker_username', // MQTT broker username
  password: 'your_mqtt_broker_password', // MQTT broker password
  setCleanSession: true, // clean session option
  connectionTimeout: 30, // connection timeout in seconds
  keepAliveInterval: 60, // keep alive interval in seconds
  setAutomaticReconnect: true, // automatic reconnect option
};

// connect to MQTT broker with options
MqttBridge.connect(connectionOptions)
  .then(() => {
    // connection successful
    console.log('Connect Success');
  })
  .catch((error: any) => {
    // connection failed with error message
    console.log('Connect Failed:', error);
  });
```

you can also add optional connect options parameter like `lastWill` to the `connectOptions`:

```typescript
  setLastWill: {
    willTopic: "your_last_will_topic",
    willPayload: "your_last_will_message",
    willQoS: "your_last_will_QoS",
    setRetained: false,
  }
```

<a name="#disconnect-from-broker"></a>

### Disconnecting from the MQTT Broker :

To disconnect from the MQTT broker, you can use the `disconnect()` method provided by the plugin. The following code demonstrates how to disconnect from an MQTT broker:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Disconnect from the MQTT broker
MqttBridge.disconnect()
  .then(() => {
    // The disconnection is successful
    console.log('Successfully disconnected from the MQTT broker');
  })
  .catch((errorMessage: any) => {
    // The disconnection fails
    console.log(
      'Failed to disconnect from the MQTT broker. Error:',
      errorMessage,
    );
  });
```

### Subscribing to an MQTT Topic :

To subscribe to an MQTT topic, you can use the `subscribe()` method provided by the plugin. The following code demonstrates how to subscribe to an MQTT topic:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Define the topic, qos
const topic = 'your_mqtt_topic';
const qos = 0;

// Subscribe to an MQTT topic
MqttBridge.subscribe({ topic, qos })
  // The subscription is successful
  .then((result: any) => {
    console.log('Successfully subscribed to topic:');
    console.log('Topic:', result.topic);
    console.log('QoS:', result.qos);
  })
  // The subscription fails
  .catch((errorMessage: any) => {
    console.log('Failed to subscribe to topic. Error:', errorMessage);
  });
```

### Publishing a Message to an MQTT Topic :

To publish a message to an MQTT topic, you can use the `publish()` method provided by the plugin. The following code demonstrates how to publish a message to an MQTT topic:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Define the topic, payload, qos, and retained properties for the message
const topic = 'your_mqtt_topic';
const payload = 'your_mqtt_message';
const qos = 0;
const retained = false;

// Publish the message
MqttBridge.publish({ topic, payload, qos, retained })
  .then((result: any) => {
    // The message is published successfully
    console.log('Successfully published message:');
    console.log('Topic:', result.topic);
    console.log('QoS:', result.qos);
    console.log('Payload:', result.payload);
    console.log('Retained:', result.retained);
    console.log('Message ID:', result.messageId);
  })
  .catch((errorMessage: any) => {
    // The message fails to publish
    console.log('Failed to publish message. Error:', errorMessage);
  });
```

### Listen to Incoming Messages :

To listen to incoming messages, you can add a CapacitorJS listener with this event name : `onMessageArrived`. The following code demonstrates how to publish a message to an MQTT topic:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Listen to incoming MQTT messages
MqttBridge.addListener('onMessageArrived', (result: any) => {
  console.log('Received a new message:');
  console.log('Topic:', result.topic);
  console.log('Message:', result.message);
});
```

When a message arrives, the listener will be triggered and you can access the message topic and payload in the result parameter. You can modify the code to suit your use case and do something more interesting with the incoming messages.

### Listen to ConnectComplete Event :

This event is triggered only when the connection to the MQTT broker is successfully completed. It also triggers when the client was reconnected after a connection loss. To implement this, you can add a CapacitorJS listener with the event name : `onConnectComplete`. The following code demonstrates how to listen to the ConnectComplete event:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Listen for the 'onConnectComplete' event
MqttBridge.addListener('onConnectComplete', (result: any) => {
  console.log('Successfully connected to MQTT broker:');
  console.log('Reconnected:', result.reconnected);
  console.log('Server URI:', result.serverURI);
});
```

### Listen to ConnectionLost Event :

This event is triggered only when the client loses the connection to the MQTT broker. To handle this event, you can add a CapacitorJS listener with the event name : `onConnectionLost`. The following code demonstrates how to listen to ConnectionLost event:

```typescript
import { MqttBridge } from 'capacitor-mqtt-native-plugin';

// Add a listener for when the connection is lost
MqttBridge.addListener('onConnectionLost', (result: any) => {
  console.log('Connection lost:');
  console.log('Connection status:', result.connectionStatus);
  console.log('Reason code:', result.reasonCode);
  console.log('Message:', result.message);
});
```

The event listener function receives an object result as an argument with the following properties:

- connectionStatus: _The status of the connection at the time the event was triggered._
- reasonCode: _The MQTT reason code for the connection loss._
- message: _Additional information about the connection loss._

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
