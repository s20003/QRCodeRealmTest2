package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s20003.qrcoderealmtest2.R
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityNextBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter.SettingAdapter

class NextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNextBinding

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
                str += if (v == selectedList[selectedList.size - 1] ) {
                    v
                } else {
                    "$v,"
                }
            }
            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
            println(str)
        }
        // https://www.youtube.com/watch?v=4uWc34lk2iE 7:35
    }
}
