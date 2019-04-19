package dev.weblen.aplicativodeculinaria;

import android.content.Context;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.activities.RecipeDetailActivity;
import dev.weblen.aplicativodeculinaria.utils.ContentManagerHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest extends BaseTest {

    public static final String SP_NAME = "sp_files";

    @Test
    public void performClickOnRecipe() {
        Intents.init();

        performClickOnRecipeRecyclerView(0);
        intended(hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY));

        onView(withId(R.id.ingredients_text))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_step_list_recycler_view))
                .check(matches(isDisplayed()));

        Intents.release();
    }

    @Test
    public void performClickOnRecipeAndStep() {

        performClickOnRecipeRecyclerView(0);

        boolean twoPaneMode = globalApplication.getResources().getBoolean(R.bool.isTabletDevice);
        if (!twoPaneMode) {
            Intents.init();
            performClickOnStepsRecyclerView(1);
            Intents.release();

            onView(withId(R.id.recipe_step_viewpager))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            performClickOnStepsRecyclerView(1);
            onView(withId(R.id.exo_player_view))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void performClickOnSwitchWidget() {
        globalApplication.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE).edit()
                .clear()
                .commit();

        performClickOnRecipeRecyclerView(0);

        onView(withId(R.id.switch_show_in_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        Recipe recipe = ContentManagerHelper.loadRecipe(globalApplication);

        assertNotNull(recipe);
    }

    public static void performClickOnRecipeRecyclerView(int recipePosition) {
        onView(withId(R.id.recipes_recycler_view)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.recipes_recycler_view)).perform(RecyclerViewActions.scrollToPosition(recipePosition));
        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipePosition, click()));
    }

    public static void performClickOnStepsRecyclerView(int recipeStep) {
        onView(withId(R.id.recipe_step_list_recycler_view)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.recipe_step_list_recycler_view)).perform(RecyclerViewActions.scrollToPosition(recipeStep));
        onView(withId(R.id.recipe_step_list_recycler_view))
                .perform(RecyclerViewActions.actionOnItemAtPosition(recipeStep, click()));
    }
}
