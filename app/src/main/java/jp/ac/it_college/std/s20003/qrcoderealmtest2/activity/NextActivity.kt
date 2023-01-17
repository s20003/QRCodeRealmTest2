package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityNextBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter.SettingAdapter

class NextActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNextBinding

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

//        binding.textView7.text = monHour.toString()
//        binding.textView8.text = monMin.toString()

        binding.recyclerView.apply {
            adapter = SettingAdapter()
            layoutManager = LinearLayoutManager(this@NextActivity)
        }

        SettingAdapter().nameList

        binding.returnButton.setOnClickListener {
            finish()
        }

        binding.registerButton.setOnClickListener {
            val selectedList = SettingAdapter().listOfSelectedActivities()
            // println(selectedList)
//            val intent = Intent(this, DisplayActivity::class.java)
            var str = ""
            for (v in selectedList) {
                // 一つの文字列にする
                str += if (v == selectedList[selectedList.size - 1] ) {
                    v
                } else {
                    "$v,"
                }
            }
            println(str)
//            startActivity(intent)
        }
        // https://www.youtube.com/watch?v=4uWc34lk2iE 7:35
    }
}
