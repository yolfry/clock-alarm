package com.ypw.clock.alarm;

import static android.content.Context.VIBRATOR_SERVICE;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;
    private Activity activity;
    private Integer requestCode = 1;

    private static final String LOG_TAG = "AlarmReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            String message = intent.getStringExtra("message");


            // Mostrar mensaje de alarma
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();


            // Reproducir un sonido de alarma (opcional)
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.start();


            // Detener el sonido después de 5 segundos
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
            }, 7000);


            /*Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(20000);  // vibra durante 2 segundos*/

            Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(7000);


        }catch (Exception e){
            e.printStackTrace();
        }


    }



}
