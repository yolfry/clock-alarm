export interface clockAlarmPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
