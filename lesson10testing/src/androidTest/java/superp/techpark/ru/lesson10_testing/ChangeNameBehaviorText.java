package superp.techpark.ru.lesson10_testing;

import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChangeNameBehaviorText {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule
            = new ActivityTestRule<>(MainActivity.class, true, false);

    @Before
    public void initValidString() {
        Context appContext = ApplicationProvider.getApplicationContext();
        PreferenceManager.getDefaultSharedPreferences(appContext).edit().clear().apply();
        mActivityRule.launchActivity(new Intent());
    }

    @Test
    public void changeText() {
        String newName = "Ivan";
        onView(withId(R.id.update_name))
                .perform(typeText(newName), closeSoftKeyboard());

        onView(withId(R.id.update_btn)).perform(click());

        onView(withId(R.id.my_name))
                .check(matches(withText(newName)));
    }

    @Test
    public void textNotChangedAfterInvalidSupply() {
        String newName = "Oz";

        onView(withId(R.id.my_name))
                .check(matches(withText(R.string.empty_name)));

        onView(withId(R.id.update_name))
                .perform(typeText(newName), closeSoftKeyboard());

        onView(withId(R.id.update_btn)).perform(click());

        onView(withId(R.id.my_name))
                .check(matches(withText(R.string.empty_name)));
    }
}
