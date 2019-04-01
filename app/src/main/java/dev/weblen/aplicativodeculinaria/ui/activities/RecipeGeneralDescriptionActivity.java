package dev.weblen.aplicativodeculinaria.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipeGeneralDescriptionAdapter;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;
import dev.weblen.aplicativodeculinaria.ui.fragments.StepsFragment;

public class RecipeGeneralDescriptionActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "recipe_general_description";

    @BindView(R.id.recipe_list_step)
    RecyclerView mRecyclerView;

    @BindView(android.R.id.content)
    View mParentLayout;

    private boolean mTabletMode;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_general_description);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(RECIPE_KEY)) {
                mRecipe = bundle.getParcelable(RECIPE_KEY);
            } else {
                Toast.makeText(getApplicationContext(), "Failed to load recipe...", Toast.LENGTH_LONG);
                finish();
            }
        }

        mTabletMode = getResources().getBoolean(R.bool.tabletMode);

//            // If there is no fragment state and the recipe contains steps, show the 1st one
//            if (savedInstanceState == null && !mRecipe.getSteps().isEmpty()) {
//
//                if (mTabletMode) {
//                    Bundle arguments = new Bundle();
//                    arguments.putParcelable(RecipeStepDetailFragment.STEP_KEY, mRecipe.getSteps().get(position));
//                    RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.recipe_step_detail_container, fragment)
//                            .commit();
//                } else {
//                    Intent intent = new Intent(this, RecipeStepDetailActivity.class);
//                    intent.putExtra(RecipeStepDetailActivity.RECIPE_KEY, mRecipe);
//                    intent.putExtra(RecipeStepDetailActivity.STEP_SELECTED_KEY, position);
//                    startActivity(intent);
//                }
//            }

//        mRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
//        mRecyclerView.setAdapter(new RecipeGeneralDescriptionAdapter(mRecipe, new Listeners.OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//
//                if (mTabletMode) {
//                    Bundle arguments = new Bundle();
//                    arguments.putParcelable(StepsFragment.STEP_KEY, mRecipe.getSteps().get(position));
//                    StepsFragment fragment = new StepsFragment();
//                    fragment.setArguments(arguments);
//                    getSupportFragmentManager().beginTransaction()
//                            .replace(R.id.recipe_step_detail_container, fragment)
//                            .commit();
//                } else {
//                    Intent intent = new Intent(this, RecipeG.class);
//                    intent.putExtra(RecipeStepDetailActivity.RECIPE_KEY, mRecipe);
//                    intent.putExtra(RecipeStepDetailActivity.STEP_SELECTED_KEY, position);
//                    startActivity(intent);
//                }
//
//            }
//        }));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "onDestroy");
    }
}
