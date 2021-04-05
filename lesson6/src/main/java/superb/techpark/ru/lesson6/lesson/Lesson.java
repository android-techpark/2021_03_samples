package superb.techpark.ru.lesson6.lesson;

import java.util.Date;

public class Lesson {

    private int mId;
    private String mName;
    private Date mDate;
    private String mPlace;
    private boolean mIsRk;
    private int mRating;

    public Lesson(int id, String name, Date date, String place, boolean isRk, int rating) {
        mId = id;
        mName = name;
        mDate = date;
        mPlace = place;
        mIsRk = isRk;
        mRating = rating;
    }

    public String getFullDescription() {
        return getId() + " " + getName() + " " + getName();
    }

    public Lesson() {}

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Date getDate() {
        return mDate;
    }

    public String getPlace() {
        return mPlace;
    }

    public boolean isRk() {
        return mIsRk;
    }

    public int getRating() {
        return mRating;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setPlace(String place) {
        mPlace = place;
    }

    public void setIsRk(boolean rk) {
        mIsRk = rk;
    }

    public void setRating(int rating) {
        mRating = rating;
    }
}

