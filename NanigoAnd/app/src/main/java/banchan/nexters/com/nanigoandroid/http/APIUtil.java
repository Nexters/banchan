package banchan.nexters.com.nanigoandroid.http;

public class APIUtil {


    public static APIService getService() {
        return APIClient.getClient(APIUrl.BASE_URL).create(APIService.class);
    }
}
