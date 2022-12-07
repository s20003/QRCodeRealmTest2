package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import jp.ac.it_college.std.s20003.qrcoderealmtest2.R
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityAlarmDetailBinding

class AlarmDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmDetailBinding

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
        binding = ActivityAlarmDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}