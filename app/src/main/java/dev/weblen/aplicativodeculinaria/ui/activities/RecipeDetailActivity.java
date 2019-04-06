package dev.weblen.aplicativodeculinaria.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipeDetailAdapter;
import dev.weblen.aplicativodeculinaria.adapters.RecipesListAdapter;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;
import dev.weblen.aplicativodeculinaria.ui.fragments.StepsFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "recipe_general_description";
    public static final String STEP_KEY   = "recipe_general_description";

    @BindView(R.id.recipe_step_list)
    RecyclerView mRecyclerView;

    @BindView(android.R.id.content)
    View mParentLayout;

    private boolean mDeviceType;

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null && bundle.containsKey(RECIPE_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);
        } else {
//            Misc.makeSnackBar(this, mParentLayout, getString(R.string.failed_to_load_recipe), true);
            finish();
        }

        setContentView(R.layout.activity_recipe_steps);
        ButterKnife.bind(this);

//        Toolbar toolbar = findViewById(R.id.detail_toolbar);
//        setSupportActionBar(toolbar);

        // Show the Up button in the action bar and set recipes name as title.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mDeviceType = getResources().getBoolean(R.bool.tabletMode);
        if (mDeviceType) {
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
//        mRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
        mRecyclerView.setAdapter(new RecipeDetailAdapter(mRecipe, new Listeners.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                showStep(position);
            }
        }));
    }

    private void showStep(int position) {
        if (mDeviceType) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.recipe_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_add_to_widget) {
//            AppWidgetService.updateWidget(this, mRecipe);
//            Misc.makeSnackBar(this, mParentLayout, String.format(getString(R.string.added_to_widget), mRecipe.getName()), false);
//
//            return true;
//        } else
            return super.onOptionsItemSelected(item);
    }
}