package jp.ac.it_college.std.s20003.qrcoderealmtest2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_CONTENT = "content"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.setSmallIcon(android.R.drawable.ic_dialog_info)
        builder.setContentTitle("お薬の時間になりました")
        builder.setContentText("お薬を飲んでください")
        builder.setAutoCancel(true)
        builder.setDefaults(NotificationCompat.DEFAULT_ALL)
        builder.priority = NotificationCompat.PRIORITY_HIGH

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(100, builder.build())

        // https://www.youtube.com/watch?v=xSrVWFCtgaE 8:03 から
    }
}