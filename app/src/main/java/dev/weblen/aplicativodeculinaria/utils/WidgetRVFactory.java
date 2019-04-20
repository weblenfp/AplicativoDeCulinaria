package dev.weblen.aplicativodeculinaria.utils;

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.models.Recipe;

class WidgetRVFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private       Recipe  recipe;

    public WidgetRVFactory(Context context) {
        this.mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        recipe = ContentManagerHelper.loadRecipe(mContext);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (recipe != null)
            return recipe.getIngredients().size();
        else
            return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(mContext.getPackageName(), R.layout.widget_selected_recipe_list_item);

        row.setTextViewText(R.id.widget_ingredients_item_text, recipe.getIngredients().get(position).getIngredient());

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
