package com.ypw.clock.alarm;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import android.widget.Toast;
import android.media.MediaPlayer;

/**
 * Class used to create notification from timer event
 * Note: Class is being registered in Android manifest as broadcast reciever
 */
public class AlarmReceiver extends BroadcastReceiver {
    /**
     * Restore and present notification
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        String message = intent.getStringExtra("message");

         // Mostrar mensaje de alarma
         Toast.makeText(context, message, Toast.LENGTH_LONG).show();
        
         // Reproducir un sonido de alarma (opcional)
         MediaPlayer mediaPlayer = MediaPlayer.create(context, android.provider.Settings.System.DEFAULT_ALARM_ALERT_URI);
         mediaPlayer.start();

    }


}



