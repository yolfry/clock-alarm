

export type AlarmSetResult = {
  // return input value as it is
  sec: number;
  // return result
  result: boolean;
}


export interface clockAlarmPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  setAlarm(options: { sec: number, sound: boolean, title: string, text: string }): Promise<AlarmSetResult>;
}
