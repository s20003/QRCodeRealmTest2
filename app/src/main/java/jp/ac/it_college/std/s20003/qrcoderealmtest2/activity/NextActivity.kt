package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import jp.ac.it_college.std.s20003.qrcoderealmtest2.R
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityNextBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter.SettingAdapter
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Time
import jp.ac.it_college.std.s20003.qrcoderealmtest2.receiver.AlarmReceiver
import java.util.*

class NextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNextBinding

    private val config = RealmConfiguration.Builder(schema = setOf(Time::class))
        .build()
    private val realm: Realm = Realm.open(config)

    private lateinit var alarmManager: AlarmManager

    private lateinit var pendingIntent: PendingIntent

    private lateinit var calendar: Calendar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_button, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.itemId.apply {
            val intent = Intent(application, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val monHour = intent.getIntExtra("MORNING_HOUR", 24)
        val monMin = intent.getIntExtra("MORNING_MINUTE", 60)
        val noonHour = intent.getIntExtra("NOON_HOUR", 24)
        val noonMin = intent.getIntExtra("NOON_MINUTE", 60)
        val nightHour = intent.getIntExtra("NIGHT_HOUR", 24)
        val nightMin = intent.getIntExtra("NIGHT_MINUTE", 60)

        binding.recyclerView.apply {
            adapter = SettingAdapter()
            layoutManager = LinearLayoutManager(this@NextActivity)
        }

        binding.returnButton.setOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            val selectedList = SettingAdapter().listOfSelectedActivities()
            var str = ""
            for (v in selectedList) {
                // 一つの文字列にする
                str += if (v == selectedList[selectedList.size - 1]) {
                    v
                } else {
                    "$v,"
                }
            }
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
            println(str)

            // ここでデータベースへの登録と通知の設定をする
            monTime(str, monHour, monMin)
            noonTime(str, noonHour, noonMin)
            nightTime(str, nightHour, nightMin)

//      テストのためコメントにしておく
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
            selectedList.clear()
        }
        // https://www.youtube.com/watch?v=4uWc34lk2iE 7:35
    }

    private fun monTime(str: String, h: Int, m: Int) {
        if (h != 24 || m != 60) {
            realm.writeBlocking {
                copyToRealm(Time().apply {
                    notifyName = str
                    Hour = h
                    Minute = m
                })
            }
            setAlarm(str, h, m)
        }
    }

    private fun noonTime(str: String, h: Int, m: Int) {
        if (h != 24 || m != 60) {
            realm.writeBlocking {
                copyToRealm(Time().apply {
                    notifyName = str
                    Hour = h
                    Minute = m
                })
            }
            setAlarm(str, h, m)
        }
    }

    private fun nightTime(str: String, h: Int, m: Int) {
        if (h != 24 || m != 60) {
            realm.writeBlocking {
                copyToRealm(Time().apply {
                    notifyName = str
                    Hour = h
                    Minute = m
                })
            }
            setAlarm(str, h, m)
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "DrugReminderChannel"
            val description = "Channel for Alarm Manager"
            val importance: Int = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("notification_channel", name, importance)
            channel.description = description

            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun setAlarm(name: String, hour: Int, min: Int) {
        // AlarmReceiverを指定
        val range = (1..2147483647)
        val id = range.random()
        // val id = name.hashCode()
        println(id)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("NAME", name)
        intent.putExtra("ID", id)
        pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0)

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
            AlarmManager.INTERVAL_DAY,
            // AlarmManager.INTERVAL_FIFTEEN_MINUTES,
            pendingIntent
        )
    }
}
