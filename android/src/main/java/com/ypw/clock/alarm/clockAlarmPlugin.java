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
        * */

        Integer id = call.getInt("id", 1);
        String message = call.getString("message", "Alarm Clock");
        String at = call.getString("at", null);
        Boolean repeats = call.getBoolean("repeats", false);
        String every = call.getString("every", null);
        Integer count = call.getInt("count", 1);


        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent alarmServiceIntent = new Intent(getContext(), AlarmReceiver.class);
        alarmServiceIntent.putExtra("message", message);
        alarmServiceIntent.putExtra("id", id);
        PendingIntent alarmServicePendingIntent = PendingIntent.getBroadcast(getContext(), id, alarmServiceIntent, PendingIntent.FLAG_IMMUTABLE);


        Log.i("Id Alarm", id.toString());
        Log.i("Cont Alarm input", count.toString());
        Log.i("every Alarm input", every);
        Log.i("At Alarm input", at);
        Log.i("message Alarm input", message);

        try {


            //Ejecutar si solo es fecha espesifica
            if (at != null && !at.isEmpty() && at.trim().length() != 0) {

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.getDefault());

                Date date = dateFormat.parse(at);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmServicePendingIntent);

            } else if (repeats!=null && repeats) {
                //Ejecutar Cada x Tiempo
                long intervalMillis = getIntervalMillis(every, count);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, alarmServicePendingIntent);
            } else {
                // No se especificó una fecha y no se debe repetir la alarma, establecer la alarma en el tiempo actual
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmServicePendingIntent);
            }

        } catch (Exception e) {
            e.printStackTrace();
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
