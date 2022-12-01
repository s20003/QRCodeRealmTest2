package jp.ac.it_college.std.s20003.qrcoderealmtest2.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter.AlarmAdapter
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.ActivityAlarmListBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Notify

class AlarmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmListBinding

    private val config = RealmConfiguration.Builder(schema = setOf(Notify::class))
        .build()
    private val realm: Realm = Realm.open(config)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result: RealmResults<Notify> = realm.query<Notify>().find()

        binding.notifyList.apply {
            layoutManager = LinearLayoutManager(this@AlarmListActivity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            adapter = AlarmAdapter(result)
        }

        val dividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)
        binding.notifyList.addItemDecoration(dividerItemDecoration)
    }
}