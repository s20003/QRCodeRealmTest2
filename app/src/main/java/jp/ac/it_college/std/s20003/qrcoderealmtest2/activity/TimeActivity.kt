package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityTimeBinding
import java.util.Calendar

class TimeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTimeBinding

    private var monHour: Int = 7
    private var monMin: Int = 0

    private var noonHour: Int = 12
    private var noonMin: Int = 0

    private var nightHour: Int = 19
    private var nightMin: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.checkBox1.isChecked = false
        binding.checkBox2.isChecked = false
        binding.checkBox3.isChecked = false
        binding.nextButton.isEnabled = false

        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val min = cal.get(Calendar.MINUTE)

        binding.checkBox1.setOnClickListener {
            checkBoxClick()
        }
        binding.checkBox2.setOnClickListener {
            checkBoxClick()
        }
        binding.checkBox3.setOnClickListener {
            checkBoxClick()
        }

        binding.button1.setOnClickListener {
            TimePickerDialog(this, { _, hourOfDay, minute ->
                monHour = hourOfDay
                monMin = minute
            }, hour, min, false).show()
        }
        binding.button2.setOnClickListener {
            TimePickerDialog(this, { _, hourOfDay, minute ->
                noonHour = hourOfDay
                noonMin = minute
            }, hour, min, false).show()
        }
        binding.button3.setOnClickListener {
            TimePickerDialog(this, { _, hourOfDay, minute ->
                nightHour = hourOfDay
                nightMin = minute
            }, hour, min, false).show()
        }

        binding.nextButton.setOnClickListener {
            val check1 = binding.checkBox1.isChecked
            val check2 = binding.checkBox2.isChecked
            val check3 = binding.checkBox3.isChecked
            // チェックボックスにチェックが入っていたらその時間データを遷移させる
            val intent = Intent(this, NextActivity::class.java)
            if (check1) {
                intent.putExtra("MORNING_HOUR", monHour)
                intent.putExtra("MORNING_MINUTE", monMin)
            }
            if (check2) {
                intent.putExtra("NOON_HOUR", noonHour)
                intent.putExtra("NOON_MINUTE", noonMin)
            }
            if (check3) {
                intent.putExtra("NIGHT_HOUR", nightHour)
                intent.putExtra("NIGHT_MINUTE", nightMin)
            }
            startActivity(intent)
        }
    }

    private fun checkBoxClick() {
        binding.nextButton.isEnabled = binding.checkBox1.isChecked || binding.checkBox2.isChecked || binding.checkBox3.isChecked
    }
}
