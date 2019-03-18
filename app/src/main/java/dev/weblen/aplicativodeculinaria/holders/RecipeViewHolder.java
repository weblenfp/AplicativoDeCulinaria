package dev.weblen.aplicativodeculinaria.holders;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;

public class RecipeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.recipe_name)
    public TextView mTvRecipeName;

    @BindView(R.id.servings)
    public TextView mTvServings;

    @BindView(R.id.recipe_image)
    public AppCompatImageView mIvRecipe;

    public RecipeViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}
