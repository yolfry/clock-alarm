import { WebPlugin } from '@capacitor/core';

import type { AlarmSetResult, clockAlarmPlugin } from './definitions';

export class clockAlarmWeb extends WebPlugin implements clockAlarmPlugin {

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }


  // 5 min alarm : set(5 * 60)
  // clear alarm : set(0)
  async setAlarm(options: {
    sec: number;
    sound: boolean;
    title: string;
    text: string;
  }): Promise<AlarmSetResult> {

    console.log("setAlarm options: ", options);

    return Promise.resolve({
      sec: options.sec,
      result: true,
    });

  }


}
