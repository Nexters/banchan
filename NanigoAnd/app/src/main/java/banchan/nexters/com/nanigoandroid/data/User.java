package banchan.nexters.com.nanigoandroid.data;

/**
 * Created by Ellen on 2018-08-19.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * {
 * "age": 25,
 * "deviceKey": "device key",
 * "sex": "F",
 * "username": {
 * "prefix": "슬픈",
 * "postfix": "친칠라"
 * }
 * }
 */

public class User {

    @SerializedName("userAuthInfo")
    @Expose
    private Object userAuthInfo;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("deviceKey")
    @Expose
    private String deviceKey;
    @SerializedName("age")
    @Expose
    private Integer age;
    @SerializedName("useYn")
    @Expose
    private String useYn;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("updatedAt")
    @Expose
    private Object updatedAt;
    @SerializedName("createdAt")
    @Expose
    private Object createdAt;
    @SerializedName("username")
    @Expose
    private Username username;

    public Object getUserAuthInfo() {
        return userAuthInfo;
    }

    public void setUserAuthInfo(Object userAuthInfo) {
        this.userAuthInfo = userAuthInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceKey() {
        return deviceKey;
    }

    public void setDeviceKey(String deviceKey) {
        this.deviceKey = deviceKey;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getUseYn() {
        return useYn;
    }

    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }
}
