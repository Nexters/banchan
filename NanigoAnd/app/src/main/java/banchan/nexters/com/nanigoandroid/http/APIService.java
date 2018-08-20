package banchan.nexters.com.nanigoandroid.http;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;

import banchan.nexters.com.nanigoandroid.data.NameData;
import banchan.nexters.com.nanigoandroid.data.ReviewsData;
import banchan.nexters.com.nanigoandroid.data.JoinUserData;
import banchan.nexters.com.nanigoandroid.data.TestData;
import banchan.nexters.com.nanigoandroid.utils.IsOnline;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * HOW TO USE IT @ellen
 *
 * 1. service 선언
 * private APIService service = APIUtil.getService();
 *
 * 2. 통신 전 인터넷 상태 체크
 *  new IsOnline().onlineCheck(applicationContext, new IsOnline.onlineCallback() {
        @Override
        public void onSuccess() {
        }
    });

 *
 * 3. 파라미터 생성(HashMap 이용)
 *
 * params.put("userId", USERID);
 * params.put("questionId", QUESTIONID);
 *
 * 4. API통신
 * service.getUrl(params).enqueue(new Callback<JsonObject>() {
        @Override
        public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
            if (response.isSuccessful()) {
                //200 Response
                // case 1. using normal JsonObject
                String result = response.body().toString();
                JSONObject data = new JSONObject(result).getJSONObject("data");
                String value = data.getString("value");

                // case 2. using own DataType(ex. TestData)
                TestList testList = response.body().getData().getList();
                Strinv value = testList.getValue();

            }else{
                //Error Response except 200 Response
                JSONObject data = new JSONObject(response.errorBody().string()).getJSONObject("data");
                String errorMessage = data.getString("errorMessage");
            }
        }

        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
        //request fail(not found, time out, etc...)
        new AlertError().failure(activityContext, applicationContext);

        }
    });
 */
public interface APIService {

    /**
     * POST
     * parameter using hashmap & using url path
     */
    @FormUrlEncoded
    @POST(APIUrl.GETURL)
    Call<JsonObject> testUrl(@Path(value="test", encoded=true) String payUrl, @FieldMap HashMap<String, String> params);

    /**
     * GET
     * parameter using hashmap & using data model
     */
    @GET(APIUrl.GETURL)
    Call<TestData> testUrl2(@QueryMap HashMap<String, String> params);


    @GET(APIUrl.REVIEWSLIST)
    Call<ReviewsData> reviewList(@Path(value="questionId", encoded=true) String questionId, @Path(value="lastReviewId", encoded=true) String lastReviewId);

    @GET(APIUrl.USERNAME)
    Call<NameData> userName();

    @Headers("Content-Type: application/json")
    @POST(APIUrl.JOINUSER)
    Call<JsonObject> joinUser( @Body JoinUserData params);
}
