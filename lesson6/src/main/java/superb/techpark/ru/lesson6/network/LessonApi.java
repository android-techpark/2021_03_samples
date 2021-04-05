package superb.techpark.ru.lesson6.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface LessonApi {

        class LessonPlain {
            public int id;
            public String date;
            public boolean is_rk;
            public String name;
            public String place;
            public int rating;
    }

    @GET("/lessons.json")
    Call<List<LessonPlain>> getAll();

    @GET("/lessons/{id}.json")
    Call<LessonPlain> getLesson(@Path("id") int id);

    class Like {
        public int rating;

        public Like(int rating) {
            this.rating = rating;
        }
    }

    @PATCH("/lessons/{id}.json")
    Call<Like> like(@Path("id") int id, @Body Like like);
}
