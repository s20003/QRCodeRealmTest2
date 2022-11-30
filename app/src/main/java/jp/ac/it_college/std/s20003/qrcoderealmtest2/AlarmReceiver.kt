package jp.ac.it_college.std.s20003.qrcoderealmtest2

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import jp.ac.it_college.std.s20003.qrcoderealmtest2.activity.NotificationActivity
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Notification

class AlarmReceiver : BroadcastReceiver() {
    companion object {
        private const val CHANNEL_ID = "notification_channel"
    }
//    private val config = RealmConfiguration.Builder(schema = setOf(Notification::class))
//        .build()
//    private val realm: Realm = Realm.open(config)

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, NotificationActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val startApp = PendingIntent.getActivity(
            context, 0, i, 0,
        )

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("お薬アラーム")
            .setContentText("お薬を飲んでください")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(startApp)


        val notificationManagerCompat = NotificationManagerCompat.from(context)
        notificationManagerCompat.notify(123, builder.build())

        Toast.makeText(context, "アラームによる処理が実行されました。", Toast.LENGTH_LONG).show()

        // https://www.youtube.com/watch?v=xSrVWFCtgaE
    }
}