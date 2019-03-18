package dev.weblen.aplicativodeculinaria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import dev.weblen.aplicativodeculinaria.holders.RecipeViewHolder;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

public class RecipesAdapter extends RecyclerView.Adapter<RecipeViewHolder> {
    private Context                       mContext;
    private List<Recipe>                  mRecipes;
    private Listeners.OnItemClickListener mOnItemClickListener;

    public RecipesAdapter(Context context, List<Recipe> recipes, Listeners.OnItemClickListener onItemClickListener) {
        this.mContext = context;
        this.mRecipes = recipes;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
