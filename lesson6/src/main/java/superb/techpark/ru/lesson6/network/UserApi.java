package superb.techpark.ru.lesson6.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {

    class UserPlain {
        public String name;
        public String password;
    }

    @GET("users.json")
    Call<List<UserPlain>> getAll();
}
