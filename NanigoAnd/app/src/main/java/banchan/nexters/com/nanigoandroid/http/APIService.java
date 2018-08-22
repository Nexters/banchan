package banchan.nexters.com.nanigoandroid.http;

import com.google.gson.JsonObject;

import java.util.HashMap;

import banchan.nexters.com.nanigoandroid.data.CardList;
import banchan.nexters.com.nanigoandroid.data.NameData;
import banchan.nexters.com.nanigoandroid.data.QuestionData;
import banchan.nexters.com.nanigoandroid.data.Reviews;
import banchan.nexters.com.nanigoandroid.data.ReviewsData;
import banchan.nexters.com.nanigoandroid.data.TestData;
import banchan.nexters.com.nanigoandroid.data.UploadCardData;
import banchan.nexters.com.nanigoandroid.data.User;
import banchan.nexters.com.nanigoandroid.data.UserData;
import banchan.nexters.com.nanigoandroid.data.VoteCard;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * HOW TO USE IT @ellen
 * <p>
 * 1. service 선언
 * private APIService service = APIUtil.getService();
 * <p>
 * 2. 통신 전 인터넷 상태 체크
 * new IsOnline().onlineCheck(applicationContext, new IsOnline.onlineCallback() {
 *
 * @Override public void onSuccess() {
 * }
 * });
 * <p>
 * <p>
 * 3. 파라미터 생성(HashMap 이용)
 * <p>
 * params.put("userId", USERID);
 * params.put("questionId", QUESTIONID);
 * <p>
 * 4. API통신
 * service.getUrl(params).enqueue(new Callback<JsonObject>() {
 * @Override public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
 * if (response.isSuccessful()) {
 * //200 Response
 * // case 1. using normal JsonObject
 * String result = response.body().toString();
 * JSONObject data = new JSONObject(result).getJSONObject("data");
 * String value = data.getString("value");
 * <p>
 * // case 2. using own DataType(ex. TestData)
 * TestList testList = response.body().getData().getList();
 * Strinv value = testList.getValue();
 * <p>
 * }else{
 * //Error Response except 200 Response
 * JSONObject data = new JSONObject(response.errorBody().string()).getJSONObject("data");
 * String errorMessage = data.getString("errorMessage");
 * }
 * }
 * @Override public void onFailure(Call<JsonObject> call, Throwable t) {
 * //request fail(not found, time out, etc...)
 * new AlertError().failure(activityContext, applicationContext);
 * <p>
 * }
 * });
 */
public interface APIService {

    /**
     * POST
     * parameter using hashmap & using url path
     */
    @FormUrlEncoded
    @POST(APIUrl.GETURL)
    Call<JsonObject> testUrl(@Path(value = "test", encoded = true) String payUrl, @FieldMap HashMap<String, String> params);

    /**
     * GET
     * parameter using hashmap & using data model
     */
    @GET(APIUrl.GETURL)
    Call<TestData> testUrl2(@QueryMap HashMap<String, String> params);


    @GET(APIUrl.REVIEWSLIST)
    Call<ReviewsData> reviewList(@Path(value = "questionId", encoded = true) String questionId, @Path(value = "lastReviewId", encoded = true) String lastReviewId);

    @GET(APIUrl.USERNAME)
    Call<NameData> userName();

    @Headers("Content-Type: application/json")
    @POST(APIUrl.JOINUSER)
    Call<JsonObject> joinUser(@Body User params);

    @GET(APIUrl.USERINFO)
    Call<UserData> userInfo(@Path(value = "userId", encoded = true) String userId);

    @GET(APIUrl.QUESTIONINFO)
    Call<QuestionData> questionInfo(@Path(value = "questionId", encoded = true) String questionId);

    /**
     * TEST userId ; 50
     * {
     "content": "string",
     "reviewId": 0
     }

     {
     "type": "SUCCESS",
     "reason": null,
     "data": 128
     }
     */
    @Headers("Content-Type: application/json")
    @POST(APIUrl.REVIEWS)
    Call<JsonObject> insertReviews(@Body Reviews params);


    /**
     * {
     "content": "string",
     "questionId": 0,
     "userId": 0
     }

     {
     "type": "SUCCESS",
     "reason": null,
     "data": 128
     }
     */
    @Headers("Content-Type: application/json")
    @PUT(APIUrl.REVIEWS)
    Call<JsonObject> updateReviews(@Body Reviews params);



    /**
     * {
     "reviewId": 128,
     "userId": 27
     }

     {
     "type": "SUCCESS",
     "reason": null,
     "data": 33
     }
     */

    @Headers("Content-Type: application/json")
    @POST(APIUrl.REVIEWSREPORT)
    Call<JsonObject> reportReviews(@Body Reviews params);


    @DELETE(APIUrl.REVIEWSDEL)
    Call<JsonObject> deleteReviews(@Path(value = "deleteReviewId", encoded = true) String deleteReviewId);

    /**
     "detail": {
     "TXT_Q": "얘들아 어떤 사진이 더 잘 생겼어?",
     "TXT_A": "셀카",
     "TXT_B": "SM 사진",
     "IMG_A": "~~~~~~~~~~~~",
     "IMG_B": "------------"
     },
     "type": "A",
     "userId": 43
     */
    @Headers("Content-Type: application/json")
    @POST(APIUrl.UPLOADCARD)
    Call<JsonObject> uploadCard(@Body UploadCardData params);

    @GET(APIUrl.NOTVOTEDCARD)
    Call<CardList> getCardList(@Path(value = "userId", encoded = true) String userId, @Path(value = "lastOrder", encoded = true) String lastOrder, @Path(value = "count", encoded = true) String count);


    @Headers("Content-Type: application/json")
    @POST(APIUrl.VOTECARD)
    Call<JsonObject> voteCard(@Body VoteCard params);

}
