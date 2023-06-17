package com.ypw.clock.alarm;

import static android.content.Context.VIBRATOR_SERVICE;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;

public class AlarmReceiver extends BroadcastReceiver {

    private Context context;
    private Activity activity;
    private Integer requestCode = 1;

    private static final String LOG_TAG = "AlarmReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {

        try {

            String message = intent.getStringExtra("message");

            //Id de la alarma
            int id = intent.getIntExtra("id", 1);


            //Dialogo Alert Popap
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Alarma")
                    .setMessage(message)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("Alarm: ", "Alarma Aceptada");
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Lógica para eliminar la alarma
                            Log.i("Alarm: ", "Alarma Cancelada");
                        }
                    })
                    .show();


            /*Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(20000);  // vibra durante 2 segundos*/

            Vibrator vibrator = (Vibrator) context.getSystemService(VIBRATOR_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                VibrationEffect vibrationEffect = VibrationEffect.createWaveform(new long[]{0, 2000, 1000, 2000}, 0);
                vibrator.vibrate(vibrationEffect);
            } else {
                // Deprecated in API 26
                vibrator.vibrate(new long[]{0, 2000, 1000, 2000}, 0);
            }



            // Mostrar mensaje de alarma
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();


            // Reproducir un sonido de alarma (opcional)
            MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
            mediaPlayer.start();



        }catch (Exception e){
            e.printStackTrace();
        }



    }



}
