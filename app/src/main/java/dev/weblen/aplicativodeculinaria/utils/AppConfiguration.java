package dev.weblen.aplicativodeculinaria.utils;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;

public class AppConfiguration extends Application {

    @Nullable
    private RecipesIdlingResource mIdlingResource;

    public AppConfiguration() {

        if (dev.weblen.aplicativodeculinaria.BuildConfig.DEBUG) {
            initializeIdlingResource();
        }
    }

    @VisibleForTesting
    @NonNull
    private IdlingResource initializeIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipesIdlingResource();
        }
        return mIdlingResource;
    }

    public void setIdleState(boolean state) {
        if (mIdlingResource != null)
            mIdlingResource.setIdleState(state);
    }

    @Nullable
    public RecipesIdlingResource getIdlingResource() {
        return mIdlingResource;
    }
}
