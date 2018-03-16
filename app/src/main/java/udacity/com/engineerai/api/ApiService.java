package udacity.com.engineerai.api;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import udacity.com.engineerai.R;
import udacity.com.engineerai.model.Users;

/**
 * Created by amit on 15/3/18.
 */

public class ApiService {
//    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    public static class Application {

        public static Call<Users> getUsers(Context context, int offset, int limit) {
            return getApplicationService(context).getUsers(limit, offset);
        }

        private static ApiCalls.ApplicationCalls getApplicationService(Context context) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(context.getString(R.string.base_url))
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build();
            return retrofit.create(ApiCalls.ApplicationCalls.class);
        }
    }


    private static OkHttpClient getOkHttpClient() {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
//                .addInterceptor(logging)
                .build();
    }
}
