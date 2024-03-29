@file:Suppress("DEPRECATION")

package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import jp.ac.it_college.std.s20003.qrcoderealmtest2.R
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityRegisterBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Information
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    private val config = RealmConfiguration.Builder(schema = setOf(Information::class))
        .build()
    private val realm: Realm = Realm.open(config)

    private lateinit var resultQR: String

    private lateinit var hex1: String
    private lateinit var hex2: String

    private lateinit var completeQR: String   // 完成形をここに入れる

    private var num1: Int = 0
    private var num2: Int = 0
    private var num3: Int = 0
    private var productView: String = ""

    private var code1 = ""
    private var code2 = ""
    private var code3 = ""
    private var code4 = ""
    private var code5 = ""

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
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.cameraButton.setOnClickListener {
            IntentIntegrator(this).apply {
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                setBeepEnabled(false)
                setPrompt("QRコードをかざしてください")
                setTorchEnabled(true)
            }.initiateScan()
        }

        binding.registerButton.setOnClickListener {
//            val nameLists = listOf(binding.nameView1.text, binding.nameView2.text, binding.nameView3.text, binding.nameView4.text, binding.nameView5.text)
//            val usageLists = listOf(binding.usageView1.text, binding.usageView2.text, binding.usageView3.text, binding.usageView4.text, binding.usageView5.text)
//            val daysLists = listOf(binding.daysView1.text, binding.daysView2.text, binding.daysView3.text, binding.daysView4.text, binding.daysView5.text)

            val nameLists = listOf("aaa", "bbb", "ccc", "ddd", "eee")
            val usageLists = listOf("111", "222", "333", "444", "555")
            val daysLists = listOf("14", "30", "", "", "")

            val dateMutableList = mutableListOf<String>()
            for (i in daysLists) {
                dateMutableList.add(days(i.toString()))
            }

            realm.writeBlocking {
                for (i in 0..4) {
                    if (nameLists[i] == "" && usageLists[i] == "" && daysLists[i] == "" && dateMutableList[i] == "") {
                        continue
                    }
                    copyToRealm(Information().apply {
                        name = nameLists[i].toString()
                        usage = usageLists[i].toString()
                        count = daysLists[i].toString()
                        day = dateMutableList[i]
                    })
                }
            }
            finish()
        }
    }

    // QRコード読み込み部分
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(resultCode, data)
        // QRコードのバイナリ読み取り
        val rawQRCode = result.rawBytes
        hex1 = String.format("%x", rawQRCode[0])
        hex2 = String.format("%x", rawQRCode[1])

        resultQR = result.contents.toString()
        if (result.contents == null) {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
        } else {
            binaryReading()
        }
    }

    private fun binaryReading() {
        if (hex1.startsWith("3")) {
            when (hex1.substring(1, 2)) {
                "0" -> {
                    code1 = resultQR
                    Toast.makeText(this, "１つ目のQRコードを読み取りました", Toast.LENGTH_LONG).show()
                }
                "1" -> {
                    if (code1 == "") {
                        Toast.makeText(this, "順番通りに読み取ってください", Toast.LENGTH_LONG).show()
                    } else {
                        code2 = resultQR
                        if (hex2.startsWith("1")) {
                            completeQR = code1 + code2
                            nameSearch()
                            usageSearch()
                            daysSearch()
                        }
                        Toast.makeText(this, "２つ目のQRコードを読み取りました", Toast.LENGTH_LONG).show()
                    }
                }
                "2" -> {
                    if (code1 == "" || code2 == "") {
                        Toast.makeText(this, "順番通りに読み取ってください", Toast.LENGTH_LONG).show()
                    } else {
                        code3 = resultQR
                        if (hex2.startsWith("2")) {
                            completeQR = code1 + code2 + code3
                            nameSearch()
                            usageSearch()
                            daysSearch()
                        }
                        Toast.makeText(this, "３つ目のQRコードを読み取りました", Toast.LENGTH_LONG).show()
                    }
                }
                "3" -> {
                    if (code1 == "" || code2 == "" || code3 == "") {
                        Toast.makeText(this, "順番通りに読み取ってください", Toast.LENGTH_LONG).show()
                    } else {
                        code4 = resultQR
                        if (hex2.startsWith("3")) {
                            completeQR = code1 + code2 + code3 + code4
                            nameSearch()
                            usageSearch()
                            daysSearch()
                        }
                        Toast.makeText(this, "４つ目のQRコードを読み取りました", Toast.LENGTH_LONG).show()
                    }
                }
                "4" -> {
                    if (code1 == "" || code2 == "" || code3 == "" || code4 == "") {
                        Toast.makeText(this, "順番通りに読み取ってください", Toast.LENGTH_LONG).show()
                    } else {
                        code5 = resultQR
                        if (hex2.startsWith("4")) {
                            completeQR = code1 + code2 + code3 + code4 + code5
                            nameSearch()
                            usageSearch()
                            daysSearch()
                        }
                        Toast.makeText(this, "５つ目のQRコードを読み取りました", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            if (resultQR.substring(0, 4) != "JAHIS") {
                Toast.makeText(this, "指定のQRコードを読み取ってください", Toast.LENGTH_LONG).show()
            } else {
                nameSearch()
                usageSearch()
                daysSearch()
            }
        }
    }

    // 抽出部分
    private fun nameSearch() {
        var startPoint: Int
        var namePosition = -1

        // 指定のQRコード以外を読み取ったときの処理を書く
        for (i in 1 until 10) {
            startPoint = namePosition + 1
            namePosition = completeQR.indexOf("201,", startPoint)
            if (namePosition == -1) {
                break
            }
            num1 = i
            val extractView = completeQR.substring(namePosition + 6)
            val commaPosition = extractView.indexOf(",")
            if (commaPosition != -1) {
                productView = extractView.substring(0, commaPosition)
                nameInput()
            } else {
                productView = extractView.substring(0)
                nameInput()
            }
        }
    }

    private fun usageSearch() {
        var startPoint: Int
        var namePosition = -1

        // 指定のQRコード以外を読み取ったときの処理を書く
        for (i in 1 until 10) {
            startPoint = namePosition + 1
            namePosition = completeQR.indexOf("301,", startPoint)
            if (namePosition == -1) {
                break
            }
            num2 = i
            val extractView = completeQR.substring(namePosition + 6)
            val commaPosition = extractView.indexOf(",")
            if (commaPosition != -1) {
                productView = extractView.substring(0, commaPosition)
                usageInput()
            } else {
                productView = extractView.substring(0)
                usageInput()
            }
        }
    }

    private fun daysSearch() {
        var startPoint: Int
        var namePosition = -1
        for (i in 1 until 10) {
            startPoint = namePosition + 1
            namePosition = completeQR.indexOf("301,", startPoint)
            if (namePosition == -1) {
                break
            }
            num3 = i
            val extractView = completeQR.substring(namePosition + 6)
            val commaPosition1 = extractView.indexOf(",")
            val viewProduct = extractView.substring(0, commaPosition1 + 10)
            val daysPosition = viewProduct.indexOf("日分")
            if (daysPosition == -1) {
                productView = 0.toString()
            } else {
                val daysProduct = viewProduct.substring(daysPosition - 3)
                val commaPosition2 = daysProduct.indexOf(",")
                val daysView = daysProduct.substring(0, commaPosition2)
                productView = daysView
                daysInput()
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun days(date: String): String {
        var day = ""
        if (date != "") {
            val a = date.toIntOrNull()
            // 引数から日を取れるようにする
            val cal = Calendar.getInstance()
            cal.time = Date()
            val df: DateFormat = SimpleDateFormat("yyyy年MM月dd日")

            if (a != null) {
                cal.add(Calendar.DATE, a)
            }
            println(df.format(cal.time))
            day = df.format(cal.time)
        }
        return day
    }

    // textView に入れる
    private fun nameInput() {
        when (num1) {
            1 -> {
                binding.nameView1.text = productView
            }
            2 -> {
                binding.nameView2.text = productView
            }
            3 -> {
                binding.nameView3.text = productView
            }
            4 -> {
                binding.nameView4.text = productView
            }
            5 -> {
                binding.nameView5.text = productView
            }
        }
    }

    private fun usageInput() {
        when (num2) {
            1 -> {
                binding.usageView1.text = productView
            }
            2 -> {
                binding.usageView2.text = productView
            }
            3 -> {
                binding.usageView3.text = productView
            }
            4 -> {
                binding.usageView4.text = productView
            }
            5 -> {
                binding.usageView5.text = productView
            }
        }
    }

    private fun daysInput() {
        when (num3) {
            1 -> {
                // mtl.add(0, productView)
                binding.daysView1.text = productView
            }
            2 -> {
                // mtl.add(1, productView)
                binding.daysView2.text = productView
            }
            3 -> {
                // mtl.add(2, productView)
                binding.daysView3.text = productView
            }
            4 -> {
                // mtl.add(3, productView)
                binding.daysView4.text = productView
            }
            5 -> {
                // mtl.add(4, productView)
                binding.daysView5.text = productView
            }
        }
    }
}