package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestMain {
    @SerializedName("list")
    @Expose
    private java.util.List<TestList> list = null;

    public java.util.List<TestList> getList() {
        return list;
    }

    public void setList(java.util.List<TestList> list) {
        this.list = list;
    }

}
