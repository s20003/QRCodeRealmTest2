package jp.ac.it_college.std.s20003.qrcoderealmtest2

import android.content.Intent
import android.telecom.Call.Details
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.InformationItemBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Information

class InformationAdapter(private val data: RealmResults<Information>) : RecyclerView.Adapter<InformationAdapter.ViewHolder>() {

    private lateinit var listener: OnInfoCellClickListener

    private val config = RealmConfiguration.Builder(schema = setOf(Information::class))
        .build()
    private val realm: Realm = Realm.open(config)

    interface OnInfoCellClickListener {
        fun onItemClick(info: Information)
    }

    // 各要素を表示するためのViewを保持する
    class ViewHolder(val binding: InformationItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setOnInfoCellClickListener(listener: OnInfoCellClickListener) {
        this.listener = listener
    }

//    class InfoListRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        InformationItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // ViewHolder クラスが ViewBinding を持ってるので、それを経由して View の更新をする。
        holder.binding.name.text = data[position].name
        holder.binding.usage.text = data[position].usage
        /*
        holder.binding.deleteButton.setOnClickListener {
            // Realmからデータを削除する
            realm.writeBlocking {
                val info = this.query<Information>("id == $0", data[position].id)
                    .find()
                    .first()
                this.delete(info)
            }
            // layout おかしい
            data.drop(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)
        }
         */
        holder.itemView.setOnClickListener {
            // listener.onItemClick(data[position])
            // Toast.makeText(holder.itemView.context, data[position].name, Toast.LENGTH_LONG).show()
            val context = holder.itemView.context
            context.startActivity(Intent(context, DetailActivity::class.java))
        }
    }

    override fun getItemCount(): Int = data.size
}