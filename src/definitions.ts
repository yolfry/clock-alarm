

export interface alarmResult {
  result: boolean
}

export interface clockAlarmPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  setAlarm(options: { sec: number, message: string }): Promise<alarmResult>;
}
