package id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.helloworld;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import androidx.test.rule.ActivityTestRule;
import android.widget.*;
import id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.helloworld.MainActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest{
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void init() {
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("id.ac.ui.cs.mobileprogramming.rasikaayuningtyas.helloworld", appContext.getPackageName());
    }

    @Test
    public void testEditText() {
        MainActivity mainActivity = activityTestRule.getActivity();
        EditText editText = mainActivity.findViewById(R.id.editTextTextPersonName);
        assertNotNull(editText);
    }







}