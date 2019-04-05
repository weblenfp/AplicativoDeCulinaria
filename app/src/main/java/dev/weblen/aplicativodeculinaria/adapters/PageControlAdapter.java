package dev.weblen.aplicativodeculinaria.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.models.Step;
import dev.weblen.aplicativodeculinaria.ui.activities.MediaPlayerInstructionsActivity;
import dev.weblen.aplicativodeculinaria.ui.fragments.StepsFragment;

public class PageControlAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private List<Step> mSteps;

    public PageControlAdapter(Context context, List<Step> steps, FragmentManager fm) {
        super(fm);
        this.mContext = context;
        this.mSteps = steps;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle arguments = new Bundle();
        arguments.putParcelable(MediaPlayerInstructionsActivity.STEP_KEY, mSteps.get(position));
        StepsFragment fragment = new StepsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(mContext.getString(R.string.pageTitle), position);
    }

    @Override
    public int getCount() {
        return mSteps.size();
    }
}
