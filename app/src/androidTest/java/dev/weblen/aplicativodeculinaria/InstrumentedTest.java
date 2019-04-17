package dev.weblen.aplicativodeculinaria;

import android.content.Context;
import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.activities.RecipeDetailActivity;
import dev.weblen.aplicativodeculinaria.ui.activities.RecipeStepsActivity;
import dev.weblen.aplicativodeculinaria.utils.ContentManagerHelper;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static dev.weblen.aplicativodeculinaria.PreConditions.getMeToRecipeInfo;
import static dev.weblen.aplicativodeculinaria.PreConditions.selectRecipeStep;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest extends BaseTest {

    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        Intents.init();

        getMeToRecipeInfo(1);
        intended(hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY));

        Intents.release();
    }

    @Test
    public void clickOnRecyclerViewItem_opensRecipeInfoActivity() {

        getMeToRecipeInfo(0);

        onView(withId(R.id.ingredients_text))
                .check(matches(isDisplayed()));

        onView(withId(R.id.recipe_step_list))
                .check(matches(isDisplayed()));
    }

    @Test
    public void clickOnRecyclerViewStepItem_opensRecipeStepActivity_orFragment() {

        getMeToRecipeInfo(0);

        boolean twoPaneMode = globalApplication.getResources().getBoolean(R.bool.isTabletDevice);
        if (!twoPaneMode) {
            // Checks if the keys are present and the intent launched is RecipeStepDetailActivity
            Intents.init();
            selectRecipeStep(1);
            intended(hasComponent(RecipeStepsActivity.class.getName()));
            intended(hasExtraWithKey(RecipeStepsActivity.RECIPE_KEY));
            intended(hasExtraWithKey(RecipeStepsActivity.STEP_KEY));
            Intents.release();

            // Check TabLayout
            onView(withId(R.id.recipe_step_tab_layout))
                    .check(matches(isCompletelyDisplayed()));
        } else {
            selectRecipeStep(1);

            onView(withId(R.id.exo_player_view))
                    .check(matches(isDisplayed()));
        }
    }

    @Test
    public void checkAddWidgetButtonFunctionality() {
        // Clear the preferences values
        globalApplication.getSharedPreferences(PreConditions.SP_NAME, Context.MODE_PRIVATE).edit()
                .clear()
                .commit();

        getMeToRecipeInfo(0);

        onView(withId(R.id.switch_show_in_widget))
                .check(matches(isDisplayed()))
                .perform(click());

        // Get the recipe base64 string from the sharedPrefs
        Recipe recipe = ContentManagerHelper.loadRecipe(globalApplication);

        // Assert recipe is not null
        assertNotNull(recipe);
    }
}
