package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.getSystemService
import jp.ac.it_college.std.s20003.qrcoderealmtest2.AlarmReceiver
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // https://www.youtube.com/watch?v=xSrVWFCtgaE 20:40 から
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // createNotificationChannel()

        binding.infoButton.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
        }

        binding.qrButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.alarmButton.setOnClickListener {
            val intent = Intent(this, AlarmListActivity::class.java)
            startActivity(intent)
        }
    }
}