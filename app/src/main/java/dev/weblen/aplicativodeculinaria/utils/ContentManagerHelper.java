package dev.weblen.aplicativodeculinaria.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dev.weblen.aplicativodeculinaria.models.Recipe;

import static android.content.Context.MODE_PRIVATE;

public class ContentManagerHelper {

    // This is the sharedpreferences file that is used to save Recipe.
    private final static String SHARED_PREFERENCES_FILE = "sp_files";

    // This is the saved UserInfoDTO list json string key in sharedpreferences file..
    private final static String SHARED_PREFERENCES_SELECTED_KEY = "sp_selected_key";


    public static void saveRecipe(Context context, Recipe recipe) {

        if (recipe == null) {
            cleanSavedRecipe(context);
        } else {

            // Create Gson object
            Gson gson = new Gson();

            // Get java object list json format string
            String recipeJsonString = gson.toJson(recipe);

            // Create SharedPreferences object
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, MODE_PRIVATE);

            // Put the json format string to SharedPreferences object.
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(SHARED_PREFERENCES_SELECTED_KEY, recipeJsonString);

            editor.commit();
        }
    }

    public static Recipe loadRecipe(Context context) {

        // Create SharedPreferences object
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, MODE_PRIVATE);

        // Get saved string data in it
        String recipeJsonString = sharedPreferences.getString(SHARED_PREFERENCES_SELECTED_KEY, "");

        // Create Gson object and translate the json string to related java object array.
        Gson   gson    = new Gson();
        Recipe mRecipe = gson.fromJson(recipeJsonString, Recipe.class);

        return mRecipe;
    }

    public static boolean checkSavedRecipe(Context context, int id) {

        Recipe recipe = loadRecipe(context);

        if (recipe != null && recipe.getId().equals(id)) {
            return true;
        } else {
            return false;
        }
    }

    public static void cleanSavedRecipe(Context context) {

        // Create SharedPreferences object
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, MODE_PRIVATE);

        // Put the json format string to SharedPreferences object.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.commit();
    }
}
