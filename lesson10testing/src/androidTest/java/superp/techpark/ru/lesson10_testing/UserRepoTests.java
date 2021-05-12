package superp.techpark.ru.lesson10_testing;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserRepoTests {

    private UserRepo mUserRepo;

    @Before
    public void setUp() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();
        mUserRepo = new UserRepo(appContext, new NameValidatorImpl());
        PreferenceManager.getDefaultSharedPreferences(appContext).edit().clear().apply();
    }

    @After
    public void tearDown() {
        mUserRepo.removeListeners();
    }

    @Test
    public void updateSuccess() {
        String name = "Ivan";
        mUserRepo.updateName(name);
        assertEquals(name, mUserRepo.getName());
    }

    @Test
    public void updateWithValidation() {
        mUserRepo.updateName("Oz");
        assertEquals("", mUserRepo.getName());
    }
}
