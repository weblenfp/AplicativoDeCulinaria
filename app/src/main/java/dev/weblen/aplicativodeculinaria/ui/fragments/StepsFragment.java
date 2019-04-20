package dev.weblen.aplicativodeculinaria.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dev.weblen.aplicativodeculinaria.R;
import dev.weblen.aplicativodeculinaria.models.Step;
import dev.weblen.aplicativodeculinaria.utils.NetworkHelper;

public class StepsFragment extends Fragment {
    public static final  String STEP_KEY            = "step_fragment_key";
    private static final String POSITION_KEY        = "pos_fragment_key";
    private static final String PLAY_WHEN_READY_KEY = "play_when_ready_key";

    @BindView(R.id.instructions_container)
    NestedScrollView mInstructionsContainer;

    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView mExoPlayerView;

    @BindView(R.id.step_thumbnail_image)
    ImageView mIvThumbnail;

    @BindView(R.id.instruction_text)
    TextView mTvInstructions;

    private SimpleExoPlayer mExoPlayer;
    private Step            mStep;
    private Unbinder        unbinder;
    private long            mCurrentPosition = 0;
    private boolean         mPlayWhenReady   = true;


    public StepsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(STEP_KEY)) {
            mStep = getArguments().getParcelable(STEP_KEY);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_step_detail, container, false);

        if (savedInstanceState != null && savedInstanceState.containsKey(POSITION_KEY)) {
            mCurrentPosition = savedInstanceState.getLong(POSITION_KEY);
            mPlayWhenReady = savedInstanceState.getBoolean(PLAY_WHEN_READY_KEY);
        }

        unbinder = ButterKnife.bind(this, rootView);

        mTvInstructions.setText(mStep.getDescription());

        if (!mStep.getThumbnailURL().isEmpty()) {
            Picasso.with(getContext())
                    .load(mStep.getThumbnailURL())
                    .placeholder(R.drawable.ic_cake)
                    .into(mIvThumbnail);
            mIvThumbnail.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (NetworkHelper.isInternetAvailable(Objects.requireNonNull(getActivity()))) {
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {
                initializePlayer(Uri.parse(mStep.getVideoURL()));
            } else {
                // Show InstructionsContainer because in case of phone landscape is hidden
                mInstructionsContainer.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getContext(), "No internet", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Log.d("StepFragment", "onDestroyView");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong(POSITION_KEY, mCurrentPosition);
        outState.putBoolean(PLAY_WHEN_READY_KEY, mPlayWhenReady);
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            DefaultBandwidthMeter  bandwidthMeter             = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector          trackSelector              = new DefaultTrackSelector(videoTrackSelectionFactory);

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            mExoPlayerView.setPlayer(mExoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            MediaSource        videoSource       = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            mExoPlayer.prepare(videoSource);

            if (mCurrentPosition != 0)
                mExoPlayer.seekTo(mCurrentPosition);

            mExoPlayer.setPlayWhenReady(mPlayWhenReady);
            mExoPlayerView.setVisibility(View.VISIBLE);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mPlayWhenReady = mExoPlayer.getPlayWhenReady();
            mCurrentPosition = mExoPlayer.getCurrentPosition();

            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }
}