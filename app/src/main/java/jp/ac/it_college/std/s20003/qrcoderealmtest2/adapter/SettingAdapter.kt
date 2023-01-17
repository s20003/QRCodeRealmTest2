package jp.ac.it_college.std.s20003.qrcoderealmtest2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.R
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Information

class SettingAdapter : RecyclerView.Adapter<SettingAdapter.ViewHolderItem>() {
    companion object {
        var returnSelectValues = mutableListOf<String>()
    }

    private val config = RealmConfiguration.Builder(schema = setOf(Information::class)).build()
    private val  realm: Realm = Realm.open(config)
    private val data: RealmResults<Information> = realm.query<Information>().find()

    inner class ViewHolderItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        var selectValues: String = ""
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderItem {
        val itemXml = LayoutInflater.from(parent.context)
            .inflate(R.layout.setting_item, parent, false)
        return ViewHolderItem(itemXml)
    }

    override fun onBindViewHolder(holder: ViewHolderItem, position: Int) {
        holder.checkBox.text = data[position].name
        holder.checkBox.setOnClickListener {
            if (holder.checkBox.isChecked) {
                holder.selectValues = data[position].name
                returnSelectValues.add(holder.selectValues)
            } else {
                returnSelectValues.remove(holder.selectValues)
            }
        }
    }

    override fun getItemCount(): Int  = data.size

    fun listOfSelectedActivities(): MutableList<String> {
        return returnSelectValues
    }
}