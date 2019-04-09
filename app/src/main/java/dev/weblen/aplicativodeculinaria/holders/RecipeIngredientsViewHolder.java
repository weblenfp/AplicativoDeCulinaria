package dev.weblen.aplicativodeculinaria.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;

public class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.ingredients_text)
    public TextView mTvIngredients;
    @BindView(R.id.show_in_widget)
    public Button mShowinWidget;

    public RecipeIngredientsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);

    }
}
