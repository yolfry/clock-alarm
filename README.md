# clock-alarm

This capacitor add-on allows you to create an alarm clock for iOS and Android

## Install

```bash
npm install clock-alarm
npx cap sync
```

## Add code (iOS)

The code is automatically added with npx cap sync.

## Add code (Android)

The code is automatically added with npx cap sync.

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`setAlarm(...)`](#setalarm)
* [`removeAlarm(...)`](#removealarm)
* [Interfaces](#interfaces)

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


### setAlarm(...)

```typescript
setAlarm(options: { id: number; at?: string; message: string; every?: 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second'; count?: number; repeats?: boolean; }) => Promise<alarmResult>
```

| Param         | Type                                                                                                                                                                                         |
| ------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`options`** | <code>{ id: number; at?: string; message: string; every?: 'year' \| 'month' \| 'two-weeks' \| 'week' \| 'day' \| 'hour' \| 'minute' \| 'second'; count?: number; repeats?: boolean; }</code> |

**Returns:** <code>Promise&lt;<a href="#alarmresult">alarmResult</a>&gt;</code>

--------------------


### removeAlarm(...)

```typescript
removeAlarm(options: { id: number; }) => Promise<{ result: boolean; }>
```

| Param         | Type                         |
| ------------- | ---------------------------- |
| **`options`** | <code>{ id: number; }</code> |

**Returns:** <code>Promise&lt;{ result: boolean; }&gt;</code>

--------------------


### Interfaces


#### alarmResult

| Prop         | Type                 |
| ------------ | -------------------- |
| **`result`** | <code>boolean</code> |

</docgen-api>
