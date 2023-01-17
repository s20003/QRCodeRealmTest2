package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.R
import jp.ac.it_college.std.s20003.qrcoderealmtest2.receiver.AlarmReceiver
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

    private var name: String = ""

    private val config = RealmConfiguration.Builder(schema = setOf(Time::class))
        .build()
    private val realm: Realm = Realm.open(config)

    companion object {
        var name: String = ""
    }

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

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createNotificationChannel()

        val result: RealmResults<Time> = realm.query<Time>().find()
        // https://www.youtube.com/watch?v=7gaHk8dYteU

        name = intent.getStringExtra("NOTIFYNAME").toString()

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
            TimePickerDialog(this, { _, hourOfDay, minute ->
                this.hour = hourOfDay
                this.min = minute

                if (name == "null") {
                    val editText = AppCompatEditText(this)
                    AlertDialog.Builder(this)
                        .setTitle("名前の入力")
                        .setMessage("薬の名前を入力してください")
                        .setView(editText)
                        .setPositiveButton("OK") { _, _ ->
                            name = editText.text.toString()
                            realm.writeBlocking {
                                copyToRealm(Time().apply {
                                    notifyName = name
                                    Hour = hour
                                    Minute = min
                                })
                            }
                            setAlarm()
                            reload()
                        }
                        .setNegativeButton("キャンセル", null)
                        .create()
                        .show()
                } else {
                    realm.writeBlocking {
                        copyToRealm(Time().apply {
                            notifyName = name
                            Hour = hour
                            Minute = min
                        })
                    }
                    setAlarm()
                    reload()
                }
            }, startHour, startMinute, false).show()
        }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.timeList.addItemDecoration(dividerItemDecoration)
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
    private fun setAlarm() {
        // AlarmReceiverを指定
        val range = (1..1000)
        val id = range.random()
        val drugName = name
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.putExtra("NAME", drugName)
        intent.putExtra("ID", id)
        pendingIntent = PendingIntent.getBroadcast(this, id, intent, 0)  // 0をFLAG_ONE_SHOTにかえた

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

//        alarmManager.set(
//            AlarmManager.ELAPSED_REALTIME_WAKEUP,
//            SystemClock.elapsedRealtime() + 15 * 1000,
//            pendingIntent
//        )
    }

    override fun onRestart() {
        super.onRestart()
        reload()
    }

    private fun reload() {
        overridePendingTransition(0, 0)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        finish()

        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}