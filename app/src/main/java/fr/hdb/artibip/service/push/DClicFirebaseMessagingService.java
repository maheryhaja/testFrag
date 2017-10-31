package fr.hdb.artibip.service.push;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.androidannotations.annotations.EService;

import fr.hdb.artibip.R;
import fr.hdb.artibip.presentation.activity.MainActivity_;

@EService
public class DClicFirebaseMessagingService extends FirebaseMessagingService{

    private static final String TAG = "FirebaseMessaging";

    private static int notificationIndex = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        try {
            if (remoteMessage != null) {
                createNotification(getBaseContext(), remoteMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void createNotification(Context context, RemoteMessage remoteMessage) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        Intent resultIntent = new Intent(context, MainActivity_.class);
        Bundle bundle = new Bundle();
        bundle.putBoolean(fr.hdb.artibip.donnee.dto.constantes.push.Notification.AFTER_NOTIFICATION, true);
        bundle.putInt(fr.hdb.artibip.donnee.dto.constantes.push.Notification.ID_INTERVENTION, Integer.parseInt(remoteMessage.getData().get("idIntervention")));
        resultIntent.putExtras(bundle);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification noti = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setLargeIcon(bitmap)
                .setContentIntent(resultPendingIntent)
                .addAction(0, "1 nouveau message", resultPendingIntent)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int smallIconViewId = context.getResources().getIdentifier("right_icon", "id", android.R.class.getPackage().getName());
            if (smallIconViewId != 0) {
                if (noti.contentIntent != null)
                    noti.contentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

                if (noti.headsUpContentView != null)
                    noti.headsUpContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);

                if (noti.bigContentView != null)
                    noti.bigContentView.setViewVisibility(smallIconViewId, View.INVISIBLE);
            }
        }
        noti.defaults |= Notification.DEFAULT_LIGHTS;
        noti.defaults |= Notification.DEFAULT_VIBRATE;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(++notificationIndex, noti);
    }
}
