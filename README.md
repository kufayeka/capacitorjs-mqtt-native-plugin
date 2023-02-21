# capacitorjs-mqtt-bridge

this is a capacitorjs mqtt bridge plugin

## Install

```bash
npm install capacitorjs-mqtt-bridge
npx cap sync
```

## API

<docgen-index>

* [`connect(...)`](#connect)
* [`disconnect()`](#disconnect)
* [`subscribe(...)`](#subscribe)
* [`publish(...)`](#publish)
* [`addListener('onConnectionLost', ...)`](#addlisteneronconnectionlost)
* [`addListener('onConnectComplete', ...)`](#addlisteneronconnectcomplete)
* [`addListener('onMessageArrived', ...)`](#addlisteneronmessagearrived)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

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

--------------------


### disconnect()

```typescript
disconnect() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### subscribe(...)

```typescript
subscribe(options: { topic: string; qos: number; }) => Promise<{ topic: string; qos: number; }>
```

| Param         | Type                                         |
| ------------- | -------------------------------------------- |
| **`options`** | <code>{ topic: string; qos: number; }</code> |

**Returns:** <code>Promise&lt;{ topic: string; qos: number; }&gt;</code>

--------------------


### publish(...)

```typescript
publish(options: { topic: string; payload: string; qos: number; retained: boolean; }) => Promise<{ topic: string; payload: string; qos: number; retained: boolean; messageId: any; }>
```

| Param         | Type                                                                             |
| ------------- | -------------------------------------------------------------------------------- |
| **`options`** | <code>{ topic: string; payload: string; qos: number; retained: boolean; }</code> |

**Returns:** <code>Promise&lt;{ topic: string; payload: string; qos: number; retained: boolean; messageId: any; }&gt;</code>

--------------------


### addListener('onConnectionLost', ...)

```typescript
addListener(eventName: 'onConnectionLost', listener: onConnectionLostListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                          |
| --------------- | ----------------------------------------------------------------------------- |
| **`eventName`** | <code>'onConnectionLost'</code>                                               |
| **`listener`**  | <code><a href="#onconnectionlostlistener">onConnectionLostListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('onConnectComplete', ...)

```typescript
addListener(eventName: 'onConnectComplete', listener: onConnectCompleteListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                            |
| --------------- | ------------------------------------------------------------------------------- |
| **`eventName`** | <code>'onConnectComplete'</code>                                                |
| **`listener`**  | <code><a href="#onconnectcompletelistener">onConnectCompleteListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('onMessageArrived', ...)

```typescript
addListener(eventName: 'onMessageArrived', listener: onMessageArrivedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                          |
| --------------- | ----------------------------------------------------------------------------- |
| **`eventName`** | <code>'onMessageArrived'</code>                                               |
| **`listener`**  | <code><a href="#onmessagearrivedlistener">onMessageArrivedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


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
