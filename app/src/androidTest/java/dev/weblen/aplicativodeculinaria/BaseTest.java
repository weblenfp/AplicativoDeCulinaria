package dev.weblen.aplicativodeculinaria;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;

import androidx.test.core.app.ActivityScenario;
import dev.weblen.aplicativodeculinaria.ui.activities.MainActivity;
import dev.weblen.aplicativodeculinaria.utils.GlobalApplication;

public class BaseTest {

    protected GlobalApplication globalApplication;
    protected IdlingResource    mIdlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        globalApplication = (GlobalApplication) activityTestRule.getActivity().getApplicationContext();
        mIdlingResource = globalApplication.getIdlingResource();
        // Register Idling Resources
        IdlingRegistry.getInstance().register(mIdlingResource);
    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
