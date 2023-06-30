package com.ypw.clock.alarm;

// capacitor defalut
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

// added
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.app.Activity;
import android.os.Build;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


@CapacitorPlugin(name = "clockAlarm")
public class clockAlarmPlugin extends Plugin {


    private Context context;
    private Activity activity;

    @PluginMethod
    public void setAlarm(PluginCall call) {

        /*
        * @input
        * id
        * at
        * message
        * every
        * count
        * repeats
        * Weekday
        * hour
        * minute
        * */

        //Id Obligatorio
        Integer id = call.getInt("id", 1);
        String message = call.getString("message", "Alarm Clock");


        //Alarma espesifica
        String at = call.getString("at", null);

        //Alarma Repetir
        Boolean repeats = call.getBoolean("repeats", false);

        //Cada Xtiempo
        String every = call.getString("every", "");
        Integer count = call.getInt("count", 1);

        //WeekDay  1 dia de la semana
        Integer Weekday = call.getInt("Weekday", null);
        Integer hour = call.getInt("hour", 9);
        Integer minute = call.getInt("minute", 20);

        try {

            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            Intent alarmServiceIntent = new Intent(getContext(), AlarmReceiver.class);
            alarmServiceIntent.putExtra("message", message);
            alarmServiceIntent.putExtra("id", id);
            PendingIntent alarmServicePendingIntent = PendingIntent.getBroadcast(getContext(), id, alarmServiceIntent, PendingIntent.FLAG_IMMUTABLE);


            //Alarma que se repiten
            if(!repeats){

                //Ejecutar si solo es fecha espesifica sin repetir
                if (at != null) {
                    //JavaScript toString() //Format
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault());
                    Date date = dateFormat.parse(at);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);

                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmServicePendingIntent);
                    } else {
                        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmServicePendingIntent);
                    }
                }else{
                    //Ejecutar ahora Now
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,0, alarmServicePendingIntent);
                }

            }else{ //Repetir


                //Repetir un dia de la Semana
                if(Weekday!=null){

                    Calendar calendar = Calendar.getInstance();

                    // Establecer la hora y los minutos en el objeto Calendar
                    calendar.setTimeInMillis(System.currentTimeMillis());
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE, minute);
                    calendar.set(Calendar.SECOND, 0);


                    switch (Weekday){
                        case 1:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                            break;
                        case 2:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                            break;

                        case 3:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                            break;

                        case 4:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                            break;

                        case 5:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                            break;

                        case 6:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.FEBRUARY);
                            break;

                        case 7:
                            calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                            break;
                    }

                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * 7, alarmServicePendingIntent);

                }

                //Repetir Cada X tiempo
                if (every!=null && Weekday==null) {
                    long intervalMillis = getIntervalMillis(every, count);
                    //Ejecutar Cada x Tiempo
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, alarmServicePendingIntent);
                }

                //No se define parametro de repetir, ejecutar alarma ahora
                if(every==null && Weekday==null){
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,0, alarmServicePendingIntent);
                }

            }


            call.resolve();

        } catch (Exception e) {
            Log.e("Error Alarm ", e.getMessage());
            call.reject(e.getMessage());
        }

    }

    @PluginMethod
    public void removeAlarm(PluginCall call){

        try {
            int id = call.getInt("id");

            // Verificar que el ID de la alarma sea válido (mayor que -1)
            if (id < 0) {
                throw new IllegalArgumentException("El ID de la alarma proporcionado no es válido.");
            }

            Intent intent = new Intent(getContext(), AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), id, intent, PendingIntent.FLAG_IMMUTABLE);

            if (pendingIntent != null) {
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);
                pendingIntent.cancel();
                Log.d("Removed alarm with ID", String.valueOf(id));
                call.resolve();
            } else {
                Log.d("Alarm not exists","");
                call.reject("No existe una alarma para el ID proporcionado.");
            }
        } catch (Exception e) {
            Log.e("Error Alarm ", e.getMessage());
            call.reject(e.getMessage());
        }
    }

    private long getIntervalMillis(String every, int count) {
        switch (every) {
            case "year":
                return count * 31556952000L;
            case "month":
                return count * 2592000000L;
            case "two-weeks":
                return count * 1209600000L;
            case "week":
                return count * 604800000L;
            case "day":
                return count * 86400000L;
            case "hour":
                return count * 3600000L;
            case "minute":
                return count * 60000L;
            case "second":
                return count * 1000L;
            default:
                return 0L;
        }
    }


}
