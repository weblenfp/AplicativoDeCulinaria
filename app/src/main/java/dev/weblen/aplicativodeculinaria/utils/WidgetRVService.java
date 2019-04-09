package dev.weblen.aplicativodeculinaria.utils;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViewsService;

import dev.weblen.aplicativodeculinaria.models.Recipe;

public class WidgetRVService extends RemoteViewsService {

    public static void updateWidget(Context context, Recipe recipe) {
        WidgetContentManager.saveRecipe(context, recipe);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[]            appWidgetIds     = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        WidgetProvider.updateAppWidgets(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public RemoteViewsService.RemoteViewsFactory onGetViewFactory(Intent intent) {
        intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

        return new WidgetRVFactory(getApplicationContext());
    }
}
