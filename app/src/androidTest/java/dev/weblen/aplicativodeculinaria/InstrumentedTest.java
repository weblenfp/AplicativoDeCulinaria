package dev.weblen.aplicativodeculinaria;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import dev.weblen.aplicativodeculinaria.ui.activities.MainActivity;
import dev.weblen.aplicativodeculinaria.ui.activities.RecipeDetailActivity;
import dev.weblen.aplicativodeculinaria.utils.AppConfiguration;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    protected AppConfiguration               appConfiguration;
    protected IdlingResource                 mIdlingResource;

    public static void getMeToRecipeInfo(int recipePosition) {
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void selectRecipeStep(int recipeStep) {
        onView(withId(R.id.recipe_step_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }

    @Test
    public void ClickOnRecyclerView() {
        Intents.init();

        getMeToRecipeInfo(0);
        intended(hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY));

        Intents.release();
    }

    @Rule
    public    ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        appConfiguration = (AppConfiguration) activityTestRule.getActivity().getApplicationContext();
        mIdlingResource = appConfiguration.getIdlingResource();
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
