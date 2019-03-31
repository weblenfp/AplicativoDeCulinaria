package dev.weblen.aplicativodeculinaria.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipesAdapter;
import dev.weblen.aplicativodeculinaria.api.APICallback;
import dev.weblen.aplicativodeculinaria.api.APIRecipes;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

public class RecipesFragment extends Fragment {
    private static final String RECIPES_KEY = "all_recipes";
    @BindView(R.id.recipes_recycler_view)
    RecyclerView       mRecipesRecyclerView;
    @BindView(R.id.refresh_recycler_view)
    SwipeRefreshLayout mPullToRefresh;
    private Unbinder unbinder;
//    @BindView(R.id.refresh_recycler_view)
//    SwipeRefreshLayout mRefreshRecyclerView;

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

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    public void onPause() {
        super.onPause();

        getActivity().unregisterReceiver(networkChangeReceiver);
    }

    private void fetchRecipes() {
//        // Set SwipeRefreshLayout that refreshing in case that loadRecipes get called by the networkChangeReceiver
//        if (Misc.isNetworkAvailable(getActivity().getApplicationContext())) {
//            mPullToRefresh.setRefreshing(true);

            APIRecipes.getInstance().getRecipes(new APICallback<List<Recipe>>() {
                @Override
                public void onResponse(final List<Recipe> result) {
                    if (result != null) {
                        mRecipes = result;
                        mRecipesRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                mListener.onFragmentInteraction(mRecipes.get(position));
                            }
                        }));
//                        // Set the default recipe for the widget
//                        if (Prefs.loadRecipe(getActivity().getApplicationContext()) == null) {
//                            AppWidgetService.updateWidget(getActivity(), mRecipes.get(0));
//                        }

                    }
//                    else {
//                        Misc.makeSnackBar(getActivity(), getView(), getString(R.string.failed_to_load_data), true);
//                    }
                    formatLayout();
                }

                @Override
                public void onCancel() {
                    formatLayout();
                }

            });
//        } else {
//            Misc.makeSnackBar(getActivity(), getView(), getString(R.string.no_internet), true);
//        }
    }

    private void formatLayout() {
        boolean loaded = mRecipes != null && mRecipes.size() > 0;
        mPullToRefresh.setRefreshing(false);

        mRecipesRecyclerView.setVisibility(loaded ? View.VISIBLE : View.GONE);
//        mNoDataContainer.setVisibility(loaded ? View.GONE : View.VISIBLE);

//        globalApplication.setIdleState(true);
    }

    public RecipesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment bind view to butter knife
        View viewRoot = inflater.inflate(R.layout.fragment_recipes, container, false);
        unbinder = ButterKnife.bind(this, viewRoot);

        setLayoutMode();

        if (savedInstanceState != null && savedInstanceState.containsKey(RECIPES_KEY)) {
            mRecipes = savedInstanceState.getParcelableArrayList(RECIPES_KEY);

            mRecipesRecyclerView.setAdapter(new RecipesAdapter(getActivity().getApplicationContext(), mRecipes, new Listeners.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mListener.onFragmentInteraction(mRecipes.get(position));
                }
            }));
//            dataLoadedTakeCareLayout();
        }
        return viewRoot;

    }

    private void setLayoutMode() {

        mRecipesRecyclerView.setHasFixedSize(true);
        mRecipesRecyclerView.setVisibility(View.GONE);

        boolean twoPaneMode = false;
        if (twoPaneMode) {
            mRecipesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        } else {
            mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

//        mRecipesRecyclerView.addItemDecoration(new SpacingItemDecoration((int) getResources().getDimension(R.dimen.margin_medium)));
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
