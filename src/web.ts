import { WebPlugin } from '@capacitor/core';

import type { AlarmOptions, alarmResult, clockAlarmPlugin } from './definitions';

export class clockAlarmWeb extends WebPlugin implements clockAlarmPlugin {

  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  // Set Alarm
  async setAlarm(options: AlarmOptions): Promise<alarmResult> {

    console.log("setAlarm options: ", options);

    return Promise.resolve({
      result: true,
    });

  }


  // Remove Alarm
  async removeAlarm(options: {
    id: number;
  }): Promise<{
    result: boolean;
  }> {
    console.log("setAlarm options: ", options);
    return Promise.resolve({
      result: true,
    });

  }



}
