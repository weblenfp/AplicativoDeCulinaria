package dev.weblen.aplicativodeculinaria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import java.text.DecimalFormat;
import java.util.Locale;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.holders.RecipeIngredientsViewHolder;
import dev.weblen.aplicativodeculinaria.holders.RecipeStepsViewHolder;
import dev.weblen.aplicativodeculinaria.models.Ingredient;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;
import dev.weblen.aplicativodeculinaria.utils.ContentManagerHelper;
import dev.weblen.aplicativodeculinaria.utils.WidgetRVService;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Recipe                        mRecipe;
    private final Listeners.OnItemClickListener mOnItemClickListener;

    public RecipeDetailAdapter(Recipe recipe, Listeners.OnItemClickListener onItemClickListener) {
        this.mRecipe = recipe;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == 0) { // Ingredients
            return new RecipeIngredientsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.recipe_ingredients, viewGroup, false));
        } else { // Steps
            return new RecipeStepsViewHolder(LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.step_list_item, viewGroup, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof RecipeIngredientsViewHolder) {
            RecipeIngredientsViewHolder holderIngredients = (RecipeIngredientsViewHolder) viewHolder;

            StringBuilder ingValue = new StringBuilder();
            for (int i = 0; i < mRecipe.getIngredients().size(); i++) {
                Ingredient    ingredients = mRecipe.getIngredients().get(i);
                DecimalFormat format      = new DecimalFormat("0.#");
                String        quantity    = format.format(ingredients.getQuantity());
                ingValue.append(String.format(Locale.getDefault(), "- %s (%s %s)", ingredients.getIngredient(), quantity, ingredients.getMeasure()));
                if (i != mRecipe.getIngredients().size() - 1)
                    ingValue.append("\n");
            }
            holderIngredients.mTvIngredients.setText(ingValue.toString());

            final Context context = viewHolder.itemView.getContext();

            //Set if recipe was selected to show in widget
            holderIngredients.mSwitchShowinWidget.setChecked(ContentManagerHelper.checkSavedRecipe(context, mRecipe.getId()));

            holderIngredients.mSwitchShowinWidget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean buttonOn) {
                    if (buttonOn) {
                        WidgetRVService.updateWidget(context, mRecipe);
                    } else {
                        WidgetRVService.updateWidget(context, null);
                    }
                }
            });
        } else if (viewHolder instanceof RecipeStepsViewHolder) {
            RecipeStepsViewHolder holderSteps = (RecipeStepsViewHolder) viewHolder;
            holderSteps.mTvStepOrder.setText(String.valueOf(viewHolder.getAdapterPosition() - 1) + ".");
            holderSteps.mTvStepName.setText(mRecipe.getSteps().get(viewHolder.getAdapterPosition() - 1).getShortDescription());

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null)
                        mOnItemClickListener.onItemClick(viewHolder.getAdapterPosition() - 1);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 0;
        else
            return 1;
    }

    @Override
    public int getItemCount() {
        return mRecipe.getSteps().size() + 1;
    }
}
