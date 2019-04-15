package dev.weblen.aplicativodeculinaria.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipesListAdapter;
import dev.weblen.aplicativodeculinaria.api.APICallback;
import dev.weblen.aplicativodeculinaria.api.APIRecipes;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;
import dev.weblen.aplicativodeculinaria.ui.activities.RecipeDetailActivity;
import dev.weblen.aplicativodeculinaria.utils.AppConfiguration;
import dev.weblen.aplicativodeculinaria.utils.NetworkHelper;

public class RecipesListFragment extends Fragment {
    private static final String RECIPES_KEY = "all_recipes";
    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecipesRecyclerView;
    private Unbinder         unbinder;
    private AppConfiguration appConfiguration;

    private List<Recipe>                  mRecipes;
    private OnFragmentInteractionListener mListener;

    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (mRecipes == null) {
                fetchRecipes();
            }
        }
    };

    public RecipesListFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();

        Objects.requireNonNull(getActivity()).registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();

        Objects.requireNonNull(getActivity()).unregisterReceiver(networkChangeReceiver);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d("RecipesListFragment", "onDestroyView");
    }

    private void fetchRecipes() {

        if (NetworkHelper.isInternetAvailable(Objects.requireNonNull(getActivity()))) {

            APIRecipes.getInstance().getRecipes(new APICallback<List<Recipe>>() {
                @Override
                public void onResponse(final List<Recipe> result) {
                    if (result != null) {
                        mRecipes = result;
                        mRecipesRecyclerView.setAdapter(new RecipesListAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                mListener.onFragmentInteraction(mRecipes.get(position));
                            }
                        }));

                    } else {
                        Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), "Failed to receive recipes", Toast.LENGTH_LONG).show();
                    }
                    formatLayout();
                }

                @Override
                public void onCancel() {
                    formatLayout();
                }
            });
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void formatLayout() {
        boolean loaded = mRecipes != null && mRecipes.size() > 0;
        appConfiguration.setIdleState(true);

        mRecipesRecyclerView.setVisibility(loaded ? View.VISIBLE : View.GONE);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewRoot = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        appConfiguration = new AppConfiguration();
        appConfiguration.setIdleState(false);

        setLayoutMode();

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);

            mRecipesRecyclerView.setAdapter(new RecipesListAdapter(Objects.requireNonNull(getActivity()).getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mListener.onFragmentInteraction(mRecipes.get(position));
                }
            }));
        }
        return viewRoot;

    }

    private void setLayoutMode() {

        mRecipesRecyclerView.setHasFixedSize(true);
        mRecipesRecyclerView.setVisibility(View.GONE);

        appConfiguration = (AppConfiguration) getActivity().getApplicationContext();

        boolean mTabletDevice = getResources().getBoolean(R.bool.isTabletDevice);
        if (mTabletDevice) {
            mRecipesRecyclerView.setLayoutManager(new GridLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(), 3));
        } else {
            mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        mRecipesRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Recipe recipe);
    }
}
