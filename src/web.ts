import { WebPlugin } from '@capacitor/core';

import type { alarmResult, clockAlarmPlugin } from './definitions';

export class clockAlarmWeb extends WebPlugin implements clockAlarmPlugin {

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  // 5 min alarm : set(5 * 60)
  // clear alarm : set(0)
  async setAlarm(options: {
    id: number;
    at?: string;
    message: string;
    every?: 'year' | 'month' | 'two-weeks' | 'week' | 'day' | 'hour' | 'minute' | 'second';
    count?: number;
    repeats?: boolean;
  }): Promise<alarmResult> {

    console.log("setAlarm options: ", options);

    return Promise.resolve({
      result: true,
    });

  }


}
