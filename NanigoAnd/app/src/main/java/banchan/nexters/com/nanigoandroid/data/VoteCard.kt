package banchan.nexters.com.nanigoandroid.data

import com.google.gson.annotations.SerializedName

/**
 * Created by XNOTE on 2018-08-23.
 */
data class VoteCard(
        @SerializedName("answer") var answer: String,
        @SerializedName("questionId") var questionId: Int,
        @SerializedName("random") var random: Boolean,
        @SerializedName("userId") var userId: Int
)