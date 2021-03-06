package banchan.nexters.com.nanigoandroid.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.ButtonBarLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import banchan.nexters.com.nanigoandroid.CardUploadActivity
import banchan.nexters.com.nanigoandroid.MainActivity
import banchan.nexters.com.nanigoandroid.MyApplication
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.R.drawable.bg_chart_round_grey
import banchan.nexters.com.nanigoandroid.data.NameData
import banchan.nexters.com.nanigoandroid.data.UserData
import banchan.nexters.com.nanigoandroid.http.APIService
import banchan.nexters.com.nanigoandroid.http.APIUtil
import banchan.nexters.com.nanigoandroid.utils.IsOnline
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager
import kotlinx.android.synthetic.main.fragment_card_page.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class SpeakerPageFragment : Fragment() {
    private lateinit var btn_question_go: Button
    private lateinit var tv_speaker_count: TextView
    private lateinit var tv_speaker_guage: TextView
    private lateinit var tv_speaker_gauge_percentage: TextView
    private var service: APIService? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_page, container, false)
        btn_question_go = view.findViewById<Button>(R.id.btn_question_go)

        btn_question_go.setOnClickListener { startActivity(Intent(context, CardUploadActivity::class.java)) }

        tv_speaker_count = view.findViewById<TextView>(R.id.tv_speaker_count)
        tv_speaker_guage = view.findViewById<TextView>(R.id.tv_speaker_gauge)
        tv_speaker_gauge_percentage = view.findViewById<TextView>(R.id.tv_speaker_gauge_percentage)

        service = APIUtil.getService()
        userInfo()
        return view
    }

    fun newInstance(): SpeakerPageFragment {
        val args: Bundle = Bundle()
        val frag = SpeakerPageFragment()
        frag.arguments = args
        return frag
    }


    private fun userInfo() {
        var userId = PreferenceManager.getInstance(context).userId


        IsOnline.onlineCheck(activity) {
            MyApplication.get().progressON(activity)
            service!!.userInfo(userId).enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: retrofit2.Response<UserData>) {
                    if (response.isSuccessful && response.body()!!.type == "SUCCESS") {
                        var speaker = response.body()!!.data.speaker.toString()
                        tv_speaker_count.text =
                                speaker.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[0]
                        var percentage = speaker.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1].toInt() * 10
                        if(percentage==0)
                            tv_speaker_guage.background= context?.let { ActivityCompat.getDrawable(it,R.drawable.bg_chart_round_grey) }
                        else
                            tv_speaker_guage.background= context?.let { ActivityCompat.getDrawable(it,R.drawable.bg_chart_round_black) }

                        tv_speaker_gauge_percentage.text =
                                percentage.toString()+"%"

                        val params = TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, speaker.split("\\.".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()[1].toFloat()/10)
                        tv_speaker_guage.layoutParams = params

                    } else {
                        Toast.makeText(activity!!.applicationContext, "ERROR : " + response.body()!!.reason, Toast.LENGTH_SHORT).show()
                    }
MyApplication.get().progressOFF()                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    //request fail(not found, time out, etc...)
                    Toast.makeText(activity?.applicationContext, "onFailure", Toast.LENGTH_SHORT).show()
                    MyApplication.get().progressOFF()
                }
            })
        }

    }

    private var isVisible: Boolean? = false

    override fun onStart() {
        super.onStart()
        if (isVisible!!) {
            userInfo()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isVisible = isVisibleToUser
        if (isVisible!!) {
            userInfo()
        }
    }


}