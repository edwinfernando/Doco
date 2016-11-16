package com.domicilio.confiable.doco.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.domicilio.confiable.doco.R;

public class Utilities {
    /**
     * Metodo encargado mostrar notificaciones en la barra segun la configuracion del usuario
     * @param context un contexto
     * @param noticationId id de la notificacion, es util para cuando se desee reemplazar la notificacion existente en la barra
     * @param title titulo de la notificacion
     * @param msg mensaje de la notificacion
     * */
    public static void showNotification(Context context, int noticationId, int iconDrawelId,
                                        String title, String msg, Intent intent){
        try {
            // this is it, we'll build the notification!
            // in the addAction method, if you don't want any icon, just set the first param to 0
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                    .setContentTitle(title)
                    .setContentText(msg)
                    .setSmallIcon(iconDrawelId);
            //.addAction(R.drawable.ic_launcher, "View", pIntent);

            // intent triggered, you can add other intent for other actions
            //Intent intent = new Intent(context, SplashActivityController.class);
            if(intent != null) {
                PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
                builder.setContentIntent(pIntent);
            }

            // define sound URI, the sound to be played when there's a notification
            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            //Uri soundUri = Uri.parse("android.resource://com.wimut/" + R.raw.cute_sms_ringtone);//para usar un sonido personalizado
            builder.setSound(soundUri);

            Notification mNotification = builder.build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

            // If you want to hide the notification after it was selected, do the code below
            mNotification.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
            mNotification.ledARGB = context.getResources().getColor(R.color.colorAccent);
            mNotification.ledOnMS = 1000;
            mNotification.ledOffMS = 3000;
            notificationManager.notify(noticationId, mNotification);
        } catch (Exception e) {
            Log.e("", "showNotification", e);
        }
    }
}
