package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class ReviewsList {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("questionId")
    @Expose
    private Integer questionId;
    @SerializedName("uesrId")
    @Expose
    private Integer uesrId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("reportState")
    @Expose
    private Integer reportState;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Integer getUesrId() {
        return uesrId;
    }

    public void setUesrId(Integer uesrId) {
        this.uesrId = uesrId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
