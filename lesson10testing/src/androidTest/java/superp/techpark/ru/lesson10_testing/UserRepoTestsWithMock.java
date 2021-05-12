package superp.techpark.ru.lesson10_testing;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class UserRepoTestsWithMock {

    private UserRepo mUserRepo;
    private Context mAppContext;

    @Before
    public void setUp() {
        // Context of the app under test.
        mAppContext = ApplicationProvider.getApplicationContext();
        PreferenceManager.getDefaultSharedPreferences(mAppContext).edit().clear().apply();
    }

    @Test
    public void updateSuccess() {
        NameValidator nameValidator = Mockito.mock(NameValidator.class);
        Mockito.when(nameValidator.isValid(Mockito.anyString())).thenReturn(true);

        mUserRepo = new UserRepo(mAppContext, nameValidator);
        String name = "oz";
        mUserRepo.updateName(name);

        assertEquals(name, mUserRepo.getName());
    }

    @Test
    public void updateWithValidation() {
        NameValidator nameValidator = Mockito.mock(NameValidator.class);
        Mockito.when(nameValidator.isValid(Mockito.anyString())).thenReturn(false);

        mUserRepo = new UserRepo(mAppContext, nameValidator);
        mUserRepo.updateName("DOESNT_MATTER_WHICH_ONE");
        assertEquals("", mUserRepo.getName());
    }
}
