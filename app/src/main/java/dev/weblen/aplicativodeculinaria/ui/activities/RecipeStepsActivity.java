package dev.weblen.aplicativodeculinaria.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.PageControlAdapter;
import dev.weblen.aplicativodeculinaria.models.Recipe;

public class RecipeStepsActivity extends AppCompatActivity {

    public static final String RECIPE_KEY = "step_selected_recipe";
    public static final String STEP_KEY   = "recipe_general_description";

    @BindView(R.id.recipe_step_tab_layout)
    TabLayout mTlRecipeStep;

    @BindView(R.id.recipe_step_viewpager)
    ViewPager mVpRecipeStep;

    @BindView(android.R.id.content)
    View mParentLayout;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBar;

    private Recipe mRecipe;
    private int    mStepSelectedPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_steps_detail);

        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null && bundle.containsKey(RECIPE_KEY) && bundle.containsKey(STEP_KEY)) {
            mRecipe = bundle.getParcelable(RECIPE_KEY);
            mStepSelectedPosition = bundle.getInt(STEP_KEY);
        } else {
            finish();
        }

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setTitle(mRecipe.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        PageControlAdapter adapter = new PageControlAdapter(getApplicationContext(), mRecipe.getSteps(), getSupportFragmentManager());
        mVpRecipeStep.setAdapter(adapter);
        mTlRecipeStep.setupWithViewPager(mVpRecipeStep);
        mVpRecipeStep.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (actionBar != null) {
                    actionBar.setTitle(mRecipe.getSteps().get(position).getShortDescription());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpRecipeStep.setCurrentItem(mStepSelectedPosition);

        super.onPostResume();

        int currentOrientation = getResources().getConfiguration().orientation;

        boolean isLandscapeOrientation;
        isLandscapeOrientation = currentOrientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscapeOrientation) {
            mAppBar.setVisibility(View.GONE);
            mTlRecipeStep.setVisibility(View.GONE);
            getSupportActionBar().hide();
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            mAppBar.setVisibility(View.VISIBLE);
            mTlRecipeStep.setVisibility(View.VISIBLE);
            getSupportActionBar().show();
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        Log.d("RecipeDetailActivity", "onDestroy");
    }
}
