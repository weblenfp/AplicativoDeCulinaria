package dev.weblen.aplicativodeculinaria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Locale;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.holders.IngredientsViewHolder;
import dev.weblen.aplicativodeculinaria.holders.RecipeViewHolder;
import dev.weblen.aplicativodeculinaria.holders.StepViewHolder;
import dev.weblen.aplicativodeculinaria.models.Ingredient;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

public class RecipesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context                       mContext;
    private List<Recipe>                  mRecipes;
    private Listeners.OnItemClickListener mOnItemClickListener;
    private Recipe mRecipe;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof IngredientsViewHolder) {
            IngredientsViewHolder mViewHolder = (IngredientsViewHolder) viewHolder;

            StringBuilder ingValue = new StringBuilder();
            for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
                Ingredient ingredients = mRecipe.getIngredients().get(i);
                ingValue.append(String.format(Locale.getDefault(), "• %s (%d %s)", ingredients.getIngredient(), ingredients.getQuantity(), ingredients.getMeasure()));
                if (i != mRecipe.getIngredients().size() - 1)
                    ingValue.append("\n");
            }

            ((IngredientsViewHolder) viewHolder).mTvIngredients.setText(ingValue.toString());
        } else if (viewHolder instanceof StepViewHolder) {
            StepViewHolder mViewHolder = (StepViewHolder) viewHolder;
            ((StepViewHolder) viewHolder).mTvStepName.setText(String.valueOf(position - 1) + ".");
            ((StepViewHolder) viewHolder).mTvStepName.setText(mRecipe.getSteps().get(position - 1).getShortDescription());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(position - 1);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
