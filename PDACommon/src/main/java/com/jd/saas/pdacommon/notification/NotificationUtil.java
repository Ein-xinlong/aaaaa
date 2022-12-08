package com.jd.saas.pdacommon.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.jd.saas.pdacommon.R;

/**
 * 通知栏消息通知和动作
 *
 * @author majiheng
 */
public class NotificationUtil extends ContextWrapper {

    private final String CHANNEL_ID = "channel_id";
    private final String CHANNEL_NAME = "channel_name";

    public NotificationUtil(Context context) {
        super(context);
    }

    /**
     * 发送消息通知
     */
    public void sendNotification(String title, String content, PendingIntent pendingIntent) {
        if (Build.VERSION.SDK_INT >= 26) {
            // 适配Android8及以上
            createNotificationChannel();
        }
        Notification notification = getNotification(title, content,pendingIntent).build();
        getManager().notify(1, notification);
    }

    private NotificationManager getManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        // 灯光开启
        channel.enableLights(true);
        channel.setLightColor(Color.GREEN);
        // 震动开启
        channel.enableVibration(true);
        // 设置声音适配Android8及以上
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build();
        channel.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/" + R.raw.new_order),audioAttributes);
        getManager().createNotificationChannel(channel);
    }

    public NotificationCompat.Builder getNotification(String title, String content, PendingIntent pendingIntent) {

        // 以下是展示大图的通知
//        NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
//        style.setBigContentTitle(title);
//        style.setSummaryText(content);
//        style.bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.pic));

        // 以下是展示多文本通知
        NotificationCompat.BigTextStyle style1 = new NotificationCompat.BigTextStyle();
        style1.setBigContentTitle(title);
        style1.bigText(content);

        return new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setContentTitle(title)
                .setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/raw/" + R.raw.new_order))
                .setContentText(content)
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round))
                .setStyle(style1)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setAutoCancel(true);
    }
}
