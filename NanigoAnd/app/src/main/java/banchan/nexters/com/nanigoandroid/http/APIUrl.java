package banchan.nexters.com.nanigoandroid.http;

public class APIUrl {

    //주의) retrofit2부터는 base url 끝에 /(루트)를 꼭 기입해야 함.
    //주의) /nanigo/get(*), nanigo/get
    public static final String BASE_URL = "http://13.209.175.20:8080/";


    /**
     * GET
     */
    public static final String GETURL = "nanigo/get";


    /**
     * GET
     * Reviews List
     */
    public static final String REVIEWSLIST = "/api/reviews/question/{questionId}/last-review/{lastReviewId}";

    public static final String USERNAME = "/api/users/names";

    public static final String JOINUSER = "/api/users";

    public static final String USERINFO = "/api/users/{userId}";

    public static final String QUESTIONINFO = "/api/questionCard/{questionId}";

    public static final String REVIEWS = "/api/reviews";
    public static final String REVIEWSDEL = "/api/reviews/{deleteReviewId}";
    public static final String REVIEWSREPORT = "/api/reviews/report";

    //질문 카드
    public static final String UPLOADCARD = "/api/questionCard";
    public static final String NOTVOTEDCARD = "/api/user/{userId}/notVotedQuestions/{lastOrder}/{count}";
    public static final String MYQUESTIONCARD = "/api/user/{userId}/userMadeQuestions/{page}/{count}";
    public static final String MYVOTECARD = "/api/user/{userId}/votedQuestions/{page}/{count}";
    public static final String VOTECARD = "/api/vote";
    public static final String CARDREPORT = "/api/question/report";

}
