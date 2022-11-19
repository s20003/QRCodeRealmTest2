package jp.ac.it_college.std.s20003.qrcoderealmtest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityInformationBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Information

class InformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationBinding

    private val config = RealmConfiguration.Builder(schema = setOf(Information::class))
        .build()
    private val realm: Realm = Realm.open(config)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // recyclerViewにセットするデータ
        val result: RealmResults<Information> = realm.query<Information>().find()

        binding.infoList.apply {
            layoutManager = LinearLayoutManager(this@InformationActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = InformationAdapter(result)
        }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.infoList.addItemDecoration(dividerItemDecoration)
    }
}