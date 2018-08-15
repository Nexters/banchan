package banchan.nexters.com.nanigoandroid.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestData {
    @SerializedName("data")
    @Expose
    private TestMain data;

    public TestMain getData() {
        return data;
    }

    public void setData(TestMain data) {
        this.data = data;
    }
}
