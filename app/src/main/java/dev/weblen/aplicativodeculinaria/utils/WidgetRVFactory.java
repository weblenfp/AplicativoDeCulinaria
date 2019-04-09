package dev.weblen.aplicativodeculinaria.utils;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.models.Recipe;

public class WidgetRVFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;
    private Recipe  recipe;

    public WidgetRVFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = WidgetContentManager.loadRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_selected_recipe_list_item);

        row.setTextViewText(R.id.ingredients_text, recipe.getIngredients().get(position).getIngredient());

        return row;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
