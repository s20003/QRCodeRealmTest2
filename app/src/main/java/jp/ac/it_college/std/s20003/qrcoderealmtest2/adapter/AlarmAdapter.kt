package jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.AlarmItemBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Notify

class AlarmAdapter (private val notifyData: RealmResults<Notify>) : RecyclerView.Adapter<AlarmAdapter.ViewHolder>() {

    private val config = RealmConfiguration.Builder(schema = setOf(Notify::class))
        .build()
    private val realm: Realm = Realm.open(config)

    class ViewHolder(val binding: AlarmItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder (
        AlarmItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = notifyData.size
}