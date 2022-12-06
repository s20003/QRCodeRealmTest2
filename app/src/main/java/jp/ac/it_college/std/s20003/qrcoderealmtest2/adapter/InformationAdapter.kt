package jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.activity.DetailActivity
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.InformationItemBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Information

class InformationAdapter(private val data: RealmResults<Information>) : RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

//    private val config = RealmConfiguration.Builder(schema = setOf(Information::class))
//        .build()
//    private val realm: Realm = Realm.open(config)

    // 各要素を表示するためのViewを保持する
    class ViewHolder(val binding: InformationItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        InformationItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // ViewHolder クラスが ViewBinding を持ってるので、それを経由して View の更新をする。
        val day = if (data[position].count != "0") {
            "日分"
        } else {
            ""
        }
        holder.binding.name.text = data[position].name
        holder.binding.usage.text = data[position].usage
        if (data[position].count == "0") {
            holder.binding.days.text = ""
        } else {
            holder.binding.days.text = data[position].count + day
        }

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("POSITION", position)
            intent.putExtra("NAME", data[position].name)
            intent.putExtra("USAGE", data[position].usage)
            intent.putExtra("DAYS", data[position].day)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = data.size
}