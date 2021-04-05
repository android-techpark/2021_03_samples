package superb.techpark.ru.lesson6.lesson;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.Transformations;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superb.techpark.ru.lesson6.network.ApiRepo;
import superb.techpark.ru.lesson6.network.LessonApi;

public class LessonRepo {
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    private final static MutableLiveData<List<Lesson>> mLessons = new MutableLiveData<>();

    static {
        mLessons.setValue(Collections.<Lesson>emptyList());
    }

    private final Context mContext;
    private LessonApi mLessonApi;

    public LessonRepo(Context context) {
        mContext = context;
        mLessonApi = ApiRepo.from(mContext).getLessonApi();
    }

    public LiveData<List<Lesson>> getLessons() {
        return Transformations.map(mLessons, new Function<List<Lesson>, List<Lesson>>() {
            @Override
            public List<Lesson> apply(List<Lesson> input) {
                for (Lesson lesson : input) {
//                    lesson.setRating(9999);
                }
                return input;
            }
        });
    }

    public void refresh() {
        mLessonApi.getAll().enqueue(new Callback<List<LessonApi.LessonPlain>>() {
            @Override
            public void onResponse(Call<List<LessonApi.LessonPlain>> call,
                                   Response<List<LessonApi.LessonPlain>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mLessons.postValue(transform(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<LessonApi.LessonPlain>> call, Throwable t) {
                Log.e("LessonRepo", "Failed to load", t);
            }
        });
    }

    public void refresh(final int id) {
        mLessonApi.getLesson(id).enqueue(new Callback<LessonApi.LessonPlain>() {
            @Override
            public void onResponse(Call<LessonApi.LessonPlain> call, Response<LessonApi.LessonPlain> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        Lesson newLesson = map(response.body());
                        List<Lesson> oldList = mLessons.getValue();
                        for (int i = 0; i < oldList.size(); i++) {
                            if (oldList.get(i).getId() == id) {
                                oldList.set(i, newLesson);
                            }
                        }
                        mLessons.postValue(oldList);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LessonApi.LessonPlain> call, Throwable t) {
                Log.d("Test", "Failed to get ", t);
            }
        });
    }

    public void like(final Lesson lesson) {
        mLessonApi.like(lesson.getId(), new LessonApi.Like(lesson.getRating() + 1)).enqueue(new Callback<LessonApi.Like>() {
            @Override
            public void onResponse(Call<LessonApi.Like> call,
                                   Response<LessonApi.Like> response) {
                if (response.isSuccessful()) {
                    refresh(lesson.getId());
                }
            }

            @Override
            public void onFailure(Call<LessonApi.Like> call, Throwable t) {
                Log.d("Test", "Failed to like " + lesson.getId(), t);
                t.printStackTrace();
            }
        });
    }

    private static List<Lesson> transform(List<LessonApi.LessonPlain> plains) {
        List<Lesson> result = new ArrayList<>();
        for (LessonApi.LessonPlain lessonPlain : plains) {
            try {
                Lesson lesson = map(lessonPlain);
                result.add(lesson);
                Log.e("LessonRepo", "Loaded " + lesson.getName() + " #" + lesson.getId());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static Lesson map(LessonApi.LessonPlain lessonPlain) throws ParseException {
        return new Lesson(
                lessonPlain.id,
                lessonPlain.name,
                sSimpleDateFormat.parse(lessonPlain.date),
                lessonPlain.place,
                lessonPlain.is_rk,
                lessonPlain.rating
        );
    }
}
