package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // https://www.youtube.com/watch?v=xSrVWFCtgaE 20:40 から
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.infoButton.setOnClickListener {
            val intent = Intent(this, InformationActivity::class.java)
            startActivity(intent)
        }

        binding.qrButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.alarmButton.setOnClickListener {
//            val intent = Intent(this, NotificationActivity::class.java)
//            startActivity(intent)
            val intent = Intent(this, TimeActivity::class.java)
            startActivity(intent)
        }
    }
}