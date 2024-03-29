package dev.weblen.aplicativodeculinaria.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.holders.RecipesListViewHolder;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListViewHolder> {
    private final Context                       mContext;
    private final List<Recipe>                  mRecipes;
    private final Listeners.OnItemClickListener mOnItemClickListener;

    public RecipesListAdapter(Context applicationContext, List<Recipe> mRecipes, Listeners.OnItemClickListener onItemClickListener) {
        this.mContext = applicationContext;
        this.mRecipes = mRecipes;
        this.mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipesListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_list_item, viewGroup, false);

        return new RecipesListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipesListViewHolder viewHolder, final int position) {
        viewHolder.mTvRecipeName.setText(mRecipes.get(position).getName());
        viewHolder.mTvServings.setText(mContext.getString(R.string.servings, mRecipes.get(position).getServings()));

        setImage(viewHolder, position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(viewHolder.getAdapterPosition());
            }
        });
    }

    private void setImage(RecipesListViewHolder viewHolder, final int position) {

        String recipeImage = mRecipes.get(position).getImage();

        if (!recipeImage.isEmpty()) {
            Picasso.with(mContext)
                    .load(recipeImage)
                    .placeholder(R.drawable.ic_cake)
                    .into(viewHolder.mIvRecipe);
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}
