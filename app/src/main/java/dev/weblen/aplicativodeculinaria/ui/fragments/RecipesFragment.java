package dev.weblen.aplicativodeculinaria.ui.fragments;

import android.content.Context;
import android.net.Uri;
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
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.adapters.RecipesAdapter;
import dev.weblen.aplicativodeculinaria.models.Recipe;
import dev.weblen.aplicativodeculinaria.ui.Listeners;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RecipesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {
    private static final String RECIPES_KEY = "all_recipes";
    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecipesRecyclerView;
    @BindView(R.id.refresh_recycler_view)
    SwipeRefreshLayout mRefreshRecyclerView;

    private List<Recipe> mRecipes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String param1, String param2) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle          args     = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment bind view to butter knife
        View viewRoot = inflater.inflate(R.layout.fragment_recipes, container, false);

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
        mRecipesRecyclerView.setVisibility(View.GONE);
        mRecipesRecyclerView.setHasFixedSize(true);

        boolean twoPaneMode = false;
        if (twoPaneMode) {
            mRecipesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), 3));
        } else {
            mRecipesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        }

        mRecipesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), 8));
        mRecipesRecyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Recipe recipe) {
        if (mListener != null) {
            mListener.onFragmentInteraction(recipe);
        }
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
