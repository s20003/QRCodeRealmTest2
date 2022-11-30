package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.ObjectId
import io.realm.kotlin.types.annotations.PrimaryKey
import jp.ac.it_college.std.s20003.qrcoderealmtest2.AlarmReceiver
import jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter.TimeAdapter
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityNotificationBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Time
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    // ここにAlarmの設定を書く
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var calendar: Calendar

    private var hour: Int = 0
    private var min: Int = 0

    private val config = RealmConfiguration.Builder(schema = setOf(Time::class))
        .build()
    private val realm: Realm = Realm.open(config)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val result: RealmResults<Time> = realm.query<Time>().find()

        val name = intent.getStringExtra("NOTIFYNAME").toString()

        binding.timeList.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = TimeAdapter(result)
        }

        binding.addButton.setOnClickListener {
            calendar = Calendar.getInstance()
            val startHour = calendar.get(Calendar.HOUR_OF_DAY)
            val startMinute = calendar.get(Calendar.MINUTE)

            // TimePickerで時間を選択する
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                this.hour = hourOfDay
                this.min = minute
                realm.writeBlocking {
                    copyToRealm(Time().apply {
                        notifyName = name
                        Hour = hour
                        Minute = min
                    })
                }
                setAlarm()
            }, startHour, startMinute, false).show()
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.timeList.addItemDecoration(dividerItemDecoration)
    }

    private fun createNotificationChannel() {
        val name: CharSequence = "DrugReminderChannel"
        val description = "Channel for Alarm Manager"
        val importance: Int = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("notification_channel", name, importance)
        channel.description = description

        val manager: NotificationManager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm() {
        // val items: RealmResults<Time> = realm.query<Time>("id == $0").find()
        // AlarmReceiverを指定
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

        // アラームの時間指定
        calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, min)
            set(Calendar.SECOND, 0)
        }
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_FIFTEEN_MINUTES, // AlarmManager.INTERVAL_DAY
            pendingIntent
        )
    }
}