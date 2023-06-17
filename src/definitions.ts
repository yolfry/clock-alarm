

export interface alarmResult {
  result: boolean
}

/*
       * @input
       * id
       * at
       * message
       * every
       * count
       * repeats
       * */


export interface clockAlarmPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  setAlarm(options: {
    id: number;
    at?: string;
    message: string;
    every?: 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second';
    count?: number;
    repeats?: boolean;
  }): Promise<alarmResult>;
}
