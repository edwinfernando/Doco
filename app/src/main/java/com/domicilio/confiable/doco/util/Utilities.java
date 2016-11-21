package com.domicilio.confiable.doco.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

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

    public static Bitmap getMarkerBitmapFromView(Context context, int resId) {

        View customMarkerView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_photo_marker, null);
        ImageView markerImageView = (ImageView) customMarkerView.findViewById(R.id.profile_image_marker);

        markerImageView.setImageDrawable(roundedBitmapDrawable(context,resId,90));

        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();

        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);

        Drawable drawable = customMarkerView.getBackground();

        if (drawable != null)
            drawable.draw(canvas);

        customMarkerView.draw(canvas);
        return returnedBitmap;
    }

    public static RoundedBitmapDrawable roundedBitmapDrawable(Context context, int resId, int size){
        //extraemos el drawable en un bitmap
        Drawable originalDrawable = context.getResources().getDrawable(resId);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        originalBitmap = BitmapScaler.strechToFill(originalBitmap,size,size);
        //creamos el drawable redondeado
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), originalBitmap);
        //asignamos el CornerRadius
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());

        return  roundedDrawable;
    }

    public static double fijarNumero(double numero, int digitos) {
        double resultado;
        resultado = numero * Math.pow(10, digitos);
        resultado = Math.round(resultado);
        resultado = resultado/Math.pow(10, digitos);
        return resultado;
    }
}
