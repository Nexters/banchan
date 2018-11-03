package banchan.nexters.com.nanigoandroid.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import banchan.nexters.com.nanigoandroid.R
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class ImageUtil {

    private val TAG = "ImageUtil"

    companion object {
        val baseURL = "https://s3.ap-northeast-2.amazonaws.com/nanigo-deploy/img/"
    }

    fun bitmap2ByteArray(file: String) : String{

        val bitmap = BitmapFactory.decodeFile(file)
        val byteArray = ByteArrayOutputStream()
        val filename = file.toString()
        if (filename.contains(".jpg") || filename.contains(".jpeg")) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
        }

        val bytes = byteArray.toByteArray()
        val encodedImg = Base64.encodeToString(bytes, Base64.NO_WRAP)

        Log.i(TAG, "uploadFile encodedImg: $encodedImg")

        return encodedImg
    }

    fun byteArray2Bitmap(encodedString: String): Bitmap {
        val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        return decodedBitmap
    }

    fun setImage(context: Context, url: String, view: ImageView) {
        Picasso.get().load(url).fit().centerCrop().into(view)
        val drawable = context.getDrawable(R.drawable.bg_rounding) as GradientDrawable
        view.run {
            background = drawable
            clipToOutline = true
        }
    }

    fun setImage(context: Context, id: Int, view: ImageView) {
        Picasso.get().load(id).fit().centerCrop().into(view)
        val drawable = context.getDrawable(R.drawable.bg_rounding) as GradientDrawable
        view.run {
            background = drawable
            clipToOutline = true
        }
    }
}