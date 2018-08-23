package banchan.nexters.com.nanigoandroid.data

import com.google.gson.annotations.SerializedName

data class ReportCard(
        @SerializedName("questionId") var questionId: Int,
        @SerializedName("userId") var userId: Int
)