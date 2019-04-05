package dev.weblen.aplicativodeculinaria.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipesAdapter;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;
import dev.weblen.aplicativodeculinaria.ui.fragments.StepsFragment;

public class RecipeGeneralDescriptionActivity extends AppCompatActivity {

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

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra(Intent.EXTRA_REFERRER)) {
            mRecipe = intent.getParcelableExtra(Intent.EXTRA_REFERRER);
        } else {
            Toast.makeText(getApplicationContext(), "Failed to load recipe...", Toast.LENGTH_LONG);
            finish();
        }

        // Show the Up button in the action bar and set recipes name as title.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mTabletMode = getResources().getBoolean(R.bool.tabletMode);

            // If there is no fragment state and the recipe contains steps, show the 1st one
            if (savedInstanceState == null && !mRecipe.getSteps().isEmpty()) {
                adjustDevice(0);
            }

        mRecyclerView.setAdapter(new RecipesAdapter(mRecipe, new Listeners.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                adjustDevice(position);
            }
        }));
    }

    private void adjustDevice(int position) {
        if (mTabletMode) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(MediaPlayerInstructionsActivity.STEP_KEY, mRecipe.getSteps().get(position));
            StepsFragment fragment = new StepsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_step_detail_container, fragment)
                    .commit();
        } else {
            Intent intent = new Intent(this, MediaPlayerInstructionsActivity.class);
            intent.putExtra(MediaPlayerInstructionsActivity.RECIPE_KEY, mRecipe);
            intent.putExtra(MediaPlayerInstructionsActivity.STEP_KEY, position);
            startActivity(intent);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Destroy", "onDestroy");
    }
}
