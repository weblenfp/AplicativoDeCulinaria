package dev.weblen.aplicativodeculinaria;

import android.support.test.espresso.intent.Intents;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import dev.weblen.aplicativodeculinaria.ui.activities.RecipeDetailActivity;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static dev.weblen.aplicativodeculinaria.PreConditions.getMeToRecipeInfo;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest extends BaseTest {

    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {
        Intents.init();

        getMeToRecipeInfo(1);
        intended(hasExtraWithKey(RecipeDetailActivity.RECIPE_KEY));

        Intents.release();
    }
}
