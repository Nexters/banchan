package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ellen on 2018-08-18.
 */

public class NameData {
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("reason")
    @Expose
    private String reason;

    @SerializedName("data")
    @Expose
    private List<NameList> data = null;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public List<NameList> getData() {
        return data;
    }

    public void setData(List<NameList> data) {
        this.data = data;
    }
}
