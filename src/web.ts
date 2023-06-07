import { WebPlugin } from '@capacitor/core';

import type { clockAlarmPlugin } from './definitions';

export class clockAlarmWeb extends WebPlugin implements clockAlarmPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
