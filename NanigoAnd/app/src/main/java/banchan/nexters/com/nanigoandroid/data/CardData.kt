package banchan.nexters.com.nanigoandroid.data

import com.google.gson.annotations.SerializedName
/**
"detail": {
"TXT_Q": "얘들아 어떤 사진이 더 잘 생겼어?",
"TXT_A": "셀카",
"TXT_B": "SM 사진",
"IMG_A": "~~~~~~~~~~~~",
"IMG_B": "------------"
},
"type": "A",
"userId": 43
 */

data class UploadCardData(
        @SerializedName("detail") var detail: CardDetailData,
        @SerializedName("type") var type: String,
        @SerializedName("userId") var userId: Int
)

data class CardDetailData(
        @SerializedName("TXT_Q") var TXT_Q: String = " ",
        @SerializedName("TXT_A") var TXT_A: String = " ",
        @SerializedName("TXT_B") var TXT_B: String = " ",
        @SerializedName("IMG_Q") var IMG_Q: String = " ",
        @SerializedName("IMG_A") var IMG_A: String = " ",
        @SerializedName("IMG_B") var IMG_B: String = " "
)

data class QuestionCard(
        @SerializedName("id") var id: Int,
        @SerializedName("order") var order: Int,
        @SerializedName("username") var username: String,
        @SerializedName("type") var type: String,
        @SerializedName("decision") var decision: String,
        @SerializedName("userId") var userId: Int,
        @SerializedName("detail") var detail: CardDetailData,
        @SerializedName("vote") var vote: Vote,
        @SerializedName("review") var review: Int,
        @SerializedName("tag") var tag: Tag
)

data class Vote(
        @SerializedName("a") var a: Int,
        @SerializedName("b") var b: Int,
        @SerializedName("total") var total: Int
)

data class Tag(
        @SerializedName("new") var new: Boolean,
        @SerializedName("first") var first: Boolean,
        @SerializedName("random") var random: Boolean
)

