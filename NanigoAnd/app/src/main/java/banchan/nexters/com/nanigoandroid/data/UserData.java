package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Data{
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("speaker")
        @Expose
        private Double speaker;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Double getSpeaker() {
            return speaker;
        }

        public void setSpeaker(Double speaker) {
            this.speaker = speaker;
        }
    }
}
