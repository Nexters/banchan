package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reviews {

    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("reviewId")
    @Expose
    private Integer reviewId;
    @SerializedName("questionId")
    @Expose
    private Integer questionId;
    @SerializedName("userId")
    @Expose
    private Integer userId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
