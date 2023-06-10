# clock-alarm

This capacitor add-on allows you to create an alarm clock for iOS and Android

## Install

```bash
npm install clock-alarm
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`setAlarm(...)`](#setalarm)
* [Type Aliases](#type-aliases)

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
setAlarm(options: { sec: number; sound: boolean; title: string; text: string; }) => Promise<AlarmSetResult>
```

| Param         | Type                                                                       |
| ------------- | -------------------------------------------------------------------------- |
| **`options`** | <code>{ sec: number; sound: boolean; title: string; text: string; }</code> |

**Returns:** <code>Promise&lt;<a href="#alarmsetresult">AlarmSetResult</a>&gt;</code>

--------------------


### Type Aliases


#### AlarmSetResult

<code>{ // return input value as it is sec: number; // return result result: boolean; }</code>

</docgen-api>
