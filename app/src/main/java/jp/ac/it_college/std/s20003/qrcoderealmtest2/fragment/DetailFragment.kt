package jp.ac.it_college.std.s20003.qrcoderealmtest2.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.activity.NotificationActivity
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.FragmentDetailBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Information

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val config = RealmConfiguration.Builder(schema = setOf(Information::class))
        .build()
    private val realm: Realm = Realm.open(config)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val data: RealmResults<Information> = realm.query<Information>().find()

        val position = requireActivity().intent.getIntExtra("POSITION", 0)
        val drugName = requireActivity().intent.getStringExtra("NAME")
        val drugUsage = requireActivity().intent.getStringExtra("USAGE")
        val days = requireActivity().intent.getStringExtra("DAYS")

        binding.tvName.text = drugName
        binding.tvUsage.text = drugUsage
        binding.tvDays.text = days.toString()
        if (binding.tvDays.text != "") {
            binding.textView4.text = "期間"
        }
//        if (binding.tvDays.text != "0") {
//            binding.textView4.text = "日分"
//        } else {
//            binding.tvDays.text = ""
//        }

        binding.notificationButton.setOnClickListener {
            val intent = Intent(context, NotificationActivity::class.java)
            intent.putExtra("NOTIFYNAME", drugName)
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(binding.tvName.text.toString())
                .setMessage("本当に削除しますか")
                .setPositiveButton("削除します") { _, _ ->
                    realm.writeBlocking {
                        val info = this.query<Information>("id == $0", data[position].id)
                            .find()
                            .first()
                        this.delete(info)
                    }
                    // fragmentの削除処理を書く
                    activity?.finish()
                }
                .setNegativeButton("キャンセル", null)
                .show()
        }

        return view
    }
}