package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter.TimeAdapter
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityNotificationBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Time
import java.util.Calendar

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding

    private val config = RealmConfiguration.Builder(schema = setOf(Time::class))
        .build()
    private val realm: Realm = Realm.open(config)

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result: RealmResults<Time> = realm.query<Time>().find()

        val name = intent.getStringExtra("NOTIFYNAME").toString()

        binding.notifyList.apply {
            layoutManager = LinearLayoutManager(this@NotificationActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = TimeAdapter(result)
        }

        binding.addButton.setOnClickListener {
            val currentTime = Calendar.getInstance()
            val startHour = currentTime.get(Calendar.HOUR_OF_DAY)
            val startMinute = currentTime.get(Calendar.MINUTE)

            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                Toast.makeText(this, "${hourOfDay}:${minute} を設定しました", Toast.LENGTH_SHORT).show()
                realm.writeBlocking {
                    copyToRealm(Time().apply {
                        notifyName = name
                        time = "${hourOfDay}:${minute}"
                    })
                }
            }, startHour, startMinute, false).show()
        }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.notifyList.addItemDecoration(dividerItemDecoration)
    }
}