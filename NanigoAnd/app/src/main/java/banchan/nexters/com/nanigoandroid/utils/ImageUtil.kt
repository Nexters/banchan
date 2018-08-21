package banchan.nexters.com.nanigoandroid.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File

class ImageUtil {

    private val TAG = "ImageUtil"

    fun bitmap2ByteArray(file: File) : String{

        val bitmap = BitmapFactory.decodeFile(file.path)
        val byteArray = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArray)
        val bytes = byteArray.toByteArray()
        val encodedImg = Base64.encodeToString(bytes, Base64.DEFAULT)

        Log.i(TAG, "uploadFile : $encodedImg")

        return encodedImg
    }

    fun byteArray2Bitmap(encodedString: String): Bitmap {
        val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
        val decodedBitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        return decodedBitmap
    }
}