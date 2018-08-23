package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuestionData {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("data")
    @Expose
    private Question data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getReason() {
        return reason;
    }

    public void setReason(Object reason) {
        this.reason = reason;
    }

    public Question getData() {
        return data;
    }

    public void setData(Question data) {
        this.data = data;
    }

    public class Question {
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("order")
        @Expose
        private Integer order;
        @SerializedName("username")
        @Expose
        private String username;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("detail")
        @Expose
        private Detail detail;
        @SerializedName("writeTime")
        @Expose
        private String writeTime;
        @SerializedName("vote")
        @Expose
        private Vote vote;
        @SerializedName("review")
        @Expose
        private Integer review;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getOrder() {
            return order;
        }

        public void setOrder(Integer order) {
            this.order = order;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Detail getDetail() {
            return detail;
        }

        public void setDetail(Detail detail) {
            this.detail = detail;
        }

        public String getWriteTime() {
            return writeTime;
        }

        public void setWriteTime(String writeTime) {
            this.writeTime = writeTime;
        }

        public Vote getVote() {
            return vote;
        }

        public void setVote(Vote vote) {
            this.vote = vote;
        }

        public Integer getReview() {
            return review;
        }

        public void setReview(Integer review) {
            this.review = review;
        }
    }

    public class Vote {

        @SerializedName("a")
        @Expose
        private Integer a;
        @SerializedName("b")
        @Expose
        private Integer b;
        @SerializedName("total")
        @Expose
        private Integer total;

        public Integer getA() {
            return a;
        }

        public void setA(Integer a) {
            this.a = a;
        }

        public Integer getB() {
            return b;
        }

        public void setB(Integer b) {
            this.b = b;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

    }

    public class Detail {

        @SerializedName("IMG_A")
        @Expose
        private String imgA;
        @SerializedName("TXT_A")
        @Expose
        private String txtA;
        @SerializedName("IMG_Q")
        @Expose
        private String imgQ;
        @SerializedName("TXT_Q")
        @Expose
        private String txtQ;
        @SerializedName("TXT_B")
        @Expose
        private String txtB;
        @SerializedName("IMG_B")
        @Expose
        private String imgB;

        public String getImgA() {
            return imgA;
        }

        public void setImgA(String imgA) {
            this.imgA = imgA;
        }

        public String getTxtA() {
            return txtA;
        }

        public void setTxtA(String txtA) {
            this.txtA = txtA;
        }

        public String getImgQ() {
            return imgQ;
        }

        public void setImgQ(String imgQ) {
            this.imgQ = imgQ;
        }

        public String getTxtQ() {
            return txtQ;
        }

        public void setTxtQ(String txtQ) {
            this.txtQ = txtQ;
        }

        public String getTxtB() {
            return txtB;
        }

        public void setTxtB(String txtB) {
            this.txtB = txtB;
        }

        public String getImgB() {
            return imgB;
        }

        public void setImgB(String imgB) {
            this.imgB = imgB;
        }
    }
}
