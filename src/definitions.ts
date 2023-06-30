

export enum Weekday {
  Sunday = 1,
  Monday = 2,
  Tuesday = 3,
  Wednesday = 4,
  Thursday = 5,
  Friday = 6,
  Saturday = 7
}




export interface alarmResult {
  result: boolean
}

export type everyType = 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second';

/*
       * @input
       * id
       * at
       * message
       * every
       * count
       * repeats
       * */

export interface AlarmOptions {

  id: number;
  /**
   * .toISOString()
   * Fecha y hora en formato "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'".
   * Ejemplo: "2023-06-18T15:15:00"
   * at: Date().toISOString()
   */
  at?: string;
  message: string;
  every?: everyType;
  count?: number;
  repeats?: boolean;
  Weekday?: Weekday;
  hour?: number;
  minute?: number;


}


export interface clockAlarmPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  setAlarm(options: AlarmOptions): Promise<alarmResult>;

  removeAlarm(options: {
    id: number;
  }): Promise<{
    result: boolean
  }>;


}
