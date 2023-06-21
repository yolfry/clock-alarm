

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
    /**
     * .toString() or ISO 8601
     * Fecha y hora en formato "yyyy-MM-dd'T'HH:mm:ss".
     * Ejemplo: "2023-06-18T15:15:00"
     * at: Date().toString()
     */
    at?: string;
    message: string;
    every?: 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second';
    count?: number;
    repeats?: boolean;
  }): Promise<alarmResult>;

  removeAlarm(options: {
    id: number;
  }): Promise<{
    result: boolean
  }>;


}
