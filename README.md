# capacitorjs-mqtt-bridge

this is a capacitorjs mqtt bridge plugin

## Install

```bash
npm install capacitorjs-mqtt-bridge
npx cap sync
```

## API

<docgen-index>

* [`mqttConnect(...)`](#mqttconnect)
* [`mqttDisconnect()`](#mqttdisconnect)
* [`mqttPublish(...)`](#mqttpublish)
* [`mqttSubscribe(...)`](#mqttsubscribe)
* [`addListener('mqttConnection', ...)`](#addlistenermqttconnection)
* [`addListener('messageArrived', ...)`](#addlistenermessagearrived)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### mqttConnect(...)

```typescript
mqttConnect(mqttOpt: { serverURI: string; port: number; }) => void
```

| Param         | Type                                              |
| ------------- | ------------------------------------------------- |
| **`mqttOpt`** | <code>{ serverURI: string; port: number; }</code> |

--------------------


### mqttDisconnect()

```typescript
mqttDisconnect() => void
```

--------------------


### mqttPublish(...)

```typescript
mqttPublish(mqttPub: { topic: string; payload: string; qos: number; retained: boolean; }) => void
```

| Param         | Type                                                                             |
| ------------- | -------------------------------------------------------------------------------- |
| **`mqttPub`** | <code>{ topic: string; payload: string; qos: number; retained: boolean; }</code> |

--------------------


### mqttSubscribe(...)

```typescript
mqttSubscribe(mqttSub: { topic: string; qos: number; }) => void
```

| Param         | Type                                         |
| ------------- | -------------------------------------------- |
| **`mqttSub`** | <code>{ topic: string; qos: number; }</code> |

--------------------


### addListener('mqttConnection', ...)

```typescript
addListener(eventName: 'mqttConnection', listener: mqttConnectionListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                      |
| --------------- | ------------------------------------------------------------------------- |
| **`eventName`** | <code>'mqttConnection'</code>                                             |
| **`listener`**  | <code><a href="#mqttconnectionlistener">mqttConnectionListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### addListener('messageArrived', ...)

```typescript
addListener(eventName: 'messageArrived', listener: messageArrivedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

| Param           | Type                                                                      |
| --------------- | ------------------------------------------------------------------------- |
| **`eventName`** | <code>'messageArrived'</code>                                             |
| **`listener`**  | <code><a href="#messagearrivedlistener">messageArrivedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### mqttConnectionListener

<code>(x: any): void</code>


#### messageArrivedListener

<code>(x: any): void</code>

</docgen-api>
