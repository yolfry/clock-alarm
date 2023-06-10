package com.ypw.clock.alarm;

import android.app.AlarmManager;
import android.os.SystemClock;


// capacitor defalut
import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

// added
import android.Manifest;
import android.content.Intent;
import android.content.Context;
import android.app.PendingIntent;
import android.app.Activity;
import android.os.Vibrator ;
import android.os.VibrationEffect ;




@CapacitorPlugin(name = "clockAlarm")
@NativePlugin(
        permissions={
                Manifest.permission.VIBRATE,
                Manifest.permission.SET_ALARM,
                Manifest.permission.WAKE_LOCK
        }
)
public class clockAlarmPlugin extends Plugin {

    
    private Context context;
    private Activity activity;
    private Integer requestCode = 1;


    private clockAlarm implementation = new clockAlarm();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }


    //Agregar Alarma
    public void setAlarm(PluginCall call) {

        Integer sec = call.getInt("sec");
        String message = call.getString("message");

        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("message", message);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        long futureTimeInMillis = SystemClock.elapsedRealtime() + 1000 * sec;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureTimeInMillis, pendingIntent);


        JSObject ret = new JSObject();
        ret.put("result", true);
        call.resolve(ret);
    }



}
