package banchan.nexters.com.nanigoandroid

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import banchan.nexters.com.nanigoandroid.widget.rulers.RulerValuePickerListener
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity(), View.OnClickListener {
    var gender =0
    var age=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        btn_join_ok.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("nanigo://joinsuccess")))
        }

        initialize()

        picker_age.setIndicatorHeight(0.3F,0.3f)
        picker_age.setValuePickerListener(object : RulerValuePickerListener {
            override fun onValueChange(value: Int) {
                //Value changed and the user stopped scrolling the ruler.
                //You can consider this value as final selected value.
                age = value
            }

            override fun onIntermediateValueChange(selectedValue: Int) {
                //Value changed but the user is still scrolling the ruler.
                //This value is not final value. You can utilize this value to display the current selected value.
            }
        })    }

    fun initialize(){
        tv_join_none.isSelected=true

        tv_join_none.setOnClickListener(this)
        tv_join_male.setOnClickListener(this)
        tv_join_female.setOnClickListener(this)
    }
    fun initGender(){
        tv_join_none.isSelected=false
        tv_join_male.isSelected=false
        tv_join_female.isSelected=false
    }
    override fun onClick(v: View) {
        var id = v.id
        initGender()
        when(id){
            R.id.tv_join_male ->{
                gender=1
                tv_join_male.isSelected=true
            }
            R.id.tv_join_female -> {
                gender=2
                tv_join_female.isSelected=true
            }
            R.id.tv_join_none -> {
                gender=0
                tv_join_none.isSelected=true
            }
        }
    }
}
