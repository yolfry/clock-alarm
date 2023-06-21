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
        * */

        Integer id = call.getInt("id", 1);
        String message = call.getString("message", "Alarm Clock");
        String at = call.getString("at", "");
        Boolean repeats = call.getBoolean("repeats", false);
        String every = call.getString("every", "");
        Integer count = call.getInt("count", 1);


        try {

            AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
            Intent alarmServiceIntent = new Intent(getContext(), AlarmReceiver.class);
            alarmServiceIntent.putExtra("message", message);
            alarmServiceIntent.putExtra("id", id);
            PendingIntent alarmServicePendingIntent = PendingIntent.getBroadcast(getContext(), id, alarmServiceIntent, PendingIntent.FLAG_IMMUTABLE);

            Log.i("Input Data id", id.toString());
            Log.i("Input Data message", message);
            Log.i("Input Data at", at);
            Log.i("Input Data repeats", repeats.toString());
            Log.i("Input Data every", every);
            Log.i("Input Data count", count.toString());

            //Ejecutar si solo es fecha espesifica sin repetir
            if (at != null && !at.isEmpty() && at.trim().length() != 0 && repeats==false) {

                //JavaScript toString() //Format
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());

                Date date = dateFormat.parse(at);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmServicePendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmServicePendingIntent);
                }


            } else if (repeats && every!=null) {

                long intervalMillis = getIntervalMillis(every, count);
                //Ejecutar Cada x Tiempo
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), intervalMillis, alarmServicePendingIntent);

            } else {
                // No se especificó una fecha y no se debe repetir la alarma, establecer la alarma en el tiempo actual
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmServicePendingIntent);
                } else {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmServicePendingIntent);
                }
            }

            call.resolve();

        } catch (Exception e) {
            Log.e("Error Alarm ", e.getMessage());
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
