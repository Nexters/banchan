package banchan.nexters.com.nanigoandroid.data

import com.google.gson.annotations.SerializedName

data class CardList(
        @SerializedName("type") var type: String,
        @SerializedName("reason") var reason: String,
        @SerializedName("data") var data: List<QuestionCard>
)