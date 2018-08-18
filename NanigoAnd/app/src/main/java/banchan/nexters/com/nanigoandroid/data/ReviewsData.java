package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ReviewsData {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("reason")
    @Expose
    private Object reason;
    @SerializedName("data")
    @Expose
    private List<ReviewsList> data = null;

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

    public List<ReviewsList> getData() {
        return data;
    }

    public void setData(List<ReviewsList> data) {
        this.data = data;
    }

}
