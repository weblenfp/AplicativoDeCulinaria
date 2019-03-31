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
import dev.weblen.aplicativodeculinaria.holders.RecipesViewHolder;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

public class RecipesAdapter extends RecyclerView.Adapter<RecipesViewHolder>  {
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
    public RecipesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recipe_list_item, viewGroup, false);

        return new RecipesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipesViewHolder viewHolder, final int position) {
        viewHolder.mTvRecipeName.setText(mRecipes.get(position).getName());
        viewHolder.mTvServings.setText(mContext.getString(R.string.servings, mRecipes.get(position).getServings()));

//        String recipeImage = mRecipes.get(position).getImage();
//        if (!recipeImage.isEmpty()) {
//
//            GlideApp.with(mContext)
//                    .load(recipeImage)
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .placeholder(R.drawable.ic_dinner)
//                    .into(viewHolder.mIvRecipe);
//        }
        setImage(viewHolder, position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null)
                    mOnItemClickListener.onItemClick(position);
            }
        });
    }

    void setImage(RecipesViewHolder viewHolder, final int position) {

        String recipeImage = mRecipes.get(position).getImage();

        if (!recipeImage.isEmpty()) {
            Picasso.with(mContext)
                    .load(recipeImage)
                    .placeholder(R.drawable.ic_cake_red_24dp)
                    .into(viewHolder.mIvRecipe);
            //.error(R.drawable.ic_image_error)
        }
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }
}
