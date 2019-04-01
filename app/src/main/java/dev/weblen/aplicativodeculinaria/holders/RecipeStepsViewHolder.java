package dev.weblen.aplicativodeculinaria.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;

public class RecipeStepsViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.step_order_text)
    public TextView mTvStepOrder;

    @BindView(R.id.step_name_text)
    public TextView mTvStepName;

    public RecipeStepsViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }
}