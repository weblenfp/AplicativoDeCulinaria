package dev.weblen.aplicativodeculinaria.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import dev.weblen.aplicativodeculinaria.models.Recipe;

import static android.content.Context.MODE_PRIVATE;

public class WidgetContentManager {

    public static final String PREFS_NAME = "prefs";
    public static final String WIDGET_KEY = "widget_key";

    // This is the sharedpreferences file that is used to save UserInfoDTO list.
    private final static String SHARED_PREFERENCES_FILE_USER_INFO_LIST = "userInfoList";

    // This is the saved UserInfoDTO list jason string key in sharedpreferences file..
    private final static String SHARED_PREFERENCES_KEY_USER_INFO_LIST = "User_Info_List";

    // This is the debug log info tag which will be printed in the android monitor console.
    private final static String USER_INFO_LIST_TAG = "USER_INFO_LIST_TAG";

    public static void saveRecipe(Context context, Recipe recipe) {

        // Create Gson object.
        Gson gson = new Gson();

        // Get java object list json format string.
        String userInfoListJsonString = gson.toJson(recipe);

        // Create SharedPreferences object.
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);

        // Put the json format string to SharedPreferences object.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_USER_INFO_LIST, userInfoListJsonString);
        editor.commit();
    }

    public static Recipe loadRecipe(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO_LIST, MODE_PRIVATE);

        // Get saved string data in it.
        String userInfoListJsonString = sharedPreferences.getString(SHARED_PREFERENCES_KEY_USER_INFO_LIST, "");

        // Create Gson object and translate the json string to related java object array.
        Gson   gson    = new Gson();
        Recipe mRecipe = gson.fromJson(userInfoListJsonString, Recipe.class);

        return mRecipe;
    }

}
