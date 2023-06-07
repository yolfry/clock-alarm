import { registerPlugin } from '@capacitor/core';

import type { clockAlarmPlugin } from './definitions';

const clockAlarm = registerPlugin<clockAlarmPlugin>('clockAlarm', {
  web: () => import('./web').then(m => new m.clockAlarmWeb()),
});

export * from './definitions';
export { clockAlarm };
