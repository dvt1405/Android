package com.fitness.timer;

        import android.app.Notification;
        import android.app.NotificationChannel;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.graphics.Bitmap;
        import android.graphics.drawable.BitmapDrawable;
        import android.os.Build;
        import android.support.v4.app.NotificationCompat;
        import android.support.v4.app.NotificationManagerCompat;
        import android.util.Log;

        import com.fitness.MainActivity;
        import com.fitness.R;

public class AlarmReceiver extends BroadcastReceiver {
    NotificationCompat.Builder nBuilder;
    NotificationManager notificationManager;
    final int notificationID = 1405;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("HELLO","I'm ALARM");
        String strOffMusic = intent.getExtras().getString("extra");
        Intent myIntent = new Intent(context, Music.class);
        myIntent.putExtra("music",strOffMusic);
        // Start Notìication
        Intent rsIntent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,rsIntent,0);
        nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.bicycleclim)
                .setContentTitle("Time for education")
                .setContentText("Play now")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Play now "))
                .setContentIntent(pendingIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        ;
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID,nBuilder.build());
        context.startService(myIntent);
    }

}
