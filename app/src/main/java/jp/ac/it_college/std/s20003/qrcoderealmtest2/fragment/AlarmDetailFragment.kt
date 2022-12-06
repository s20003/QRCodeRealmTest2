package jp.ac.it_college.std.s20003.qrcoderealmtest2.fragment

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import jp.ac.it_college.std.s20003.qrcoderealmtest2.databinding.FragmentAlarmDetailBinding
import jp.ac.it_college.std.s20003.qrcoderealmtest2.model.Time

class AlarmDetailFragment : Fragment() {
    private var _binding: FragmentAlarmDetailBinding? = null
    private val binding get() = _binding!!

    private val config = RealmConfiguration.Builder(schema = setOf(Time::class))
        .build()
    private val realm: Realm = Realm.open(config)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmDetailBinding.inflate(layoutInflater)
        val view = binding.root

        val timeData: RealmResults<Time> = realm.query<Time>().find()

        val drugName = requireActivity().intent.getStringExtra("NAME")
        binding.textView6.text = drugName
        val position = requireActivity().intent.getIntExtra("POSITION", 0)

        binding.checkButton.setOnClickListener {
            binding.checkButton.setBackgroundColor(Color.rgb(128, 128, 128))
            binding.checkButton.isEnabled = false
            Toast.makeText(context, "薬を飲みました", Toast.LENGTH_LONG).show()
        }

        binding.deleteTimeButton.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle(drugName)
                .setMessage("本当に削除しますか")
                .setPositiveButton("削除します") { _, _ ->
                    realm.writeBlocking {
                        val info = this.query<Time>("id == $0", timeData[position].id)
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