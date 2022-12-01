package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityAlarmDetailBinding

class AlarmDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}