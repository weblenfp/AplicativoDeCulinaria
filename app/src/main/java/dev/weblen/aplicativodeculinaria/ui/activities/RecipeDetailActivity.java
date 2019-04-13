package dev.weblen.aplicativodeculinaria.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipeDetailAdapter;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;
import dev.weblen.aplicativodeculinaria.ui.fragments.StepsFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "recipe_general_description";

    @BindView(R.id.recipe_step_list)
    RecyclerView mRecyclerView;
    @BindView(android.R.id.content)
    View         mParentLayout;

    private boolean mTabletDevice;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);
        } else {
            finish();
        }

        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        // Show the Up button in the action bar and set recipes name as title.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTabletDevice = getResources().getBoolean(R.bool.tabletDevice);
        if (mTabletDevice) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.

            // If there is no fragment state and the recipe contains steps, show the 1st one
            if (savedInstanceState == null && !mRecipe.getSteps().isEmpty()) {
                showStep(0);
            }
        }

        setupRecyclerView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MediaPlayerActivity", "onDestroy");
    }

    private void setupRecyclerView() {
        mRecyclerView.setAdapter(new RecipeDetailAdapter(mRecipe, new Listeners.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showStep(position);
            }
        }));
    }

    private void showStep(int position) {
        if (mTabletDevice) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(StepsFragment.STEP_KEY, mRecipe.getSteps().get(position));
            StepsFragment fragment = new StepsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, RecipeStepsActivity.class);
            intent.putExtra(RecipeStepsActivity.RECIPE_KEY, mRecipe);
            intent.putExtra(RecipeStepsActivity.STEP_KEY, position);
            startActivity(intent);
        }
    }
}