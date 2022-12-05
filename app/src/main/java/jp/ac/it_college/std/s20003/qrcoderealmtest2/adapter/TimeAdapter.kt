package jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.activity.AlarmDetailActivity
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.NotificationItemBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Time

// timeDataの型をMutableListに変えてみる RealmResults<Time>
class TimeAdapter (private val timeData: RealmResults<Time>) : RecyclerView.Adapter<TimeAdapter.ViewHolder>() {

//    private val config = RealmConfiguration.Builder(schema = setOf(Time::class))
//        .build()
//    private val realm: Realm = Realm.open(config)

    class ViewHolder(val binding: NotificationItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder (
        NotificationItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.notifyName.text = timeData[position].notifyName
        holder.binding.hour.text = timeData[position].Hour.toString()
        holder.binding.minute.text = timeData[position].Minute.toString()
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, AlarmDetailActivity::class.java)
            intent.putExtra("POSITION", position)
            intent.putExtra("NAME", timeData[position].notifyName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = timeData.size
}