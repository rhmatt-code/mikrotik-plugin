# mikrotik-plugin

plugin for connect to restapi mikrotik

## Install

To use npm

```bash
npm install mikrotik-plugin
````

To use yarn

```bash
yarn add mikrotik-plugin
```

Sync native files

```bash
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`connect(...)`](#connect)
* [`getResource()`](#getresource)
* [`getLogs()`](#getlogs)
* [`getPppoeSecrets()`](#getpppoesecrets)
* [`getHotspot()`](#gethotspot)
* [`getActiveOnly()`](#getactiveonly)
* [`getIdentity()`](#getidentity)
* [`getPppoeProfile()`](#getpppoeprofile)
* [`addPppoeProfile(...)`](#addpppoeprofile)
* [`addPppoeSecret(...)`](#addpppoesecret)
* [`addHotspotProfile(...)`](#addhotspotprofile)
* [`generateHotspotVouchers(...)`](#generatehotspotvouchers)
* [`getHotspotProfiles()`](#gethotspotprofiles)
* [`editPPoE(...)`](#editppoe)
* [`deletePPoE(...)`](#deleteppoe)
* [`EditHotspot(...)`](#edithotspot)
* [`deleteHotspot(...)`](#deletehotspot)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### connect(...)

```typescript
connect(options: { ip: string; username: string; password: string; port: number; }) => Promise<any>
```

| Param         | Type                                                                           |
| ------------- | ------------------------------------------------------------------------------ |
| **`options`** | <code>{ ip: string; username: string; password: string; port: number; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getResource()

```typescript
getResource() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getLogs()

```typescript
getLogs() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getPppoeSecrets()

```typescript
getPppoeSecrets() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getHotspot()

```typescript
getHotspot() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getActiveOnly()

```typescript
getActiveOnly() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getIdentity()

```typescript
getIdentity() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getPppoeProfile()

```typescript
getPppoeProfile() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### addPppoeProfile(...)

```typescript
addPppoeProfile(data: { name: string; rate: number; }) => Promise<any>
```

| Param      | Type                                         |
| ---------- | -------------------------------------------- |
| **`data`** | <code>{ name: string; rate: number; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### addPppoeSecret(...)

```typescript
addPppoeSecret(data: { username: string; password: string; profile: string; }) => Promise<any>
```

| Param      | Type                                                                  |
| ---------- | --------------------------------------------------------------------- |
| **`data`** | <code>{ username: string; password: string; profile: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### addHotspotProfile(...)

```typescript
addHotspotProfile(data: { name: string; duration: string; rate: string; }) => Promise<any>
```

| Param      | Type                                                           |
| ---------- | -------------------------------------------------------------- |
| **`data`** | <code>{ name: string; duration: string; rate: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### generateHotspotVouchers(...)

```typescript
generateHotspotVouchers(option: any) => Promise<any>
```

| Param        | Type             |
| ------------ | ---------------- |
| **`option`** | <code>any</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### getHotspotProfiles()

```typescript
getHotspotProfiles() => Promise<any>
```

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### editPPoE(...)

```typescript
editPPoE(data: { name: string; profileNew: string; }) => Promise<any>
```

| Param      | Type                                               |
| ---------- | -------------------------------------------------- |
| **`data`** | <code>{ name: string; profileNew: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### deletePPoE(...)

```typescript
deletePPoE(data: { name: string; }) => Promise<any>
```

| Param      | Type                           |
| ---------- | ------------------------------ |
| **`data`** | <code>{ name: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### EditHotspot(...)

```typescript
EditHotspot(data: { name: string; password: string; profileNew: string; }) => Promise<any>
```

| Param      | Type                                                                 |
| ---------- | -------------------------------------------------------------------- |
| **`data`** | <code>{ name: string; password: string; profileNew: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------


### deleteHotspot(...)

```typescript
deleteHotspot(data: { name: string; }) => Promise<any>
```

| Param      | Type                           |
| ---------- | ------------------------------ |
| **`data`** | <code>{ name: string; }</code> |

**Returns:** <code>Promise&lt;any&gt;</code>

--------------------

</docgen-api>
