package udacity.com.engineerai.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import udacity.com.engineerai.model.Users;

/**
 * Created by amit on 15/3/18.
 */

public class ApiCalls {
    interface ApplicationCalls {

        /**
         * To get the all users
         * */
        @GET("/api/users")
        Call<Users> getUsers(@Query("limit") int limit, @Query("offset") int offset);

    }

}
