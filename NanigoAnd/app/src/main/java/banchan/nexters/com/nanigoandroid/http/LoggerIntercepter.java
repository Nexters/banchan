package banchan.nexters.com.nanigoandroid.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Denple cannot use retrofit library<br><br>
 *
 * API_URL Request / Response Logger class <br><br>
 *
 * Created by jongsic.kim on 2017-04-06.
 */
public class LoggerIntercepter implements Interceptor {

    String TAG = "LoggerIntercepter";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String method = request.method();

        long startMills = System.currentTimeMillis();
        Log.i(TAG, "[->][" + method + "] Request : " + request.url());

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if( copy.body() != null ) {
                copy.body().writeTo(buffer);
                Log.i(TAG,"[->]["+method+"] RequestBody : " + buffer.readUtf8());
            }
        } catch (final IOException e) {
            // do nothing
        }

        Response response = chain.proceed(request);
        String body = response.body().string();

        long endMills = System.currentTimeMillis();
        Log.i(TAG,"[<-][" + method + "] Response : " + request.url() + " (Spend Time : " + (endMills-startMills) + ")\n" + body);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), body))
                .message(body)
                .build();
    }
}
