package dev.weblen.aplicativodeculinaria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.holders.IngredientsViewHolder;
import dev.weblen.aplicativodeculinaria.holders.RecipeViewHolder;
import dev.weblen.aplicativodeculinaria.holders.StepViewHolder;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new IngredientsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recipe_ingredients, viewGroup, false));
        } else {
            return new StepViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recipe_step, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }
}
