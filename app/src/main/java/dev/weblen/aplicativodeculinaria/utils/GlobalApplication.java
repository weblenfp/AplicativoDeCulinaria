package dev.weblen.aplicativodeculinaria.utils;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;

public class GlobalApplication extends Application {

    @Nullable
    private RecipesIdlingResource mIdlingResource;

    public GlobalApplication() {

        if (dev.weblen.aplicativodeculinaria.BuildConfig.DEBUG) {
            initializeIdlingResource();
        }
    }

    @VisibleForTesting
    private void initializeIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new RecipesIdlingResource();
        }
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
