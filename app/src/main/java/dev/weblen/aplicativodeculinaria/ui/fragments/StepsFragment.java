package dev.weblen.aplicativodeculinaria.ui.fragments;

import android.content.res.Configuration;
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
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
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

import static android.view.View.SYSTEM_UI_FLAG_VISIBLE;

public class StepsFragment extends Fragment {
    public static final  String STEP_KEY            = "step_fragment_key";
    private static final String POSITION_KEY        = "pos_fragment_key";
    private static final String PLAY_WHEN_READY_KEY = "play_when_ready_key";

    @BindView(R.id.instructions_container)
    NestedScrollView    mInstructionsContainer;
    @BindView(R.id.exo_player_view)
    SimpleExoPlayerView mExoPlayerView;
    @BindView(R.id.step_thumbnail_image)
    ImageView           mIvThumbnail;
    @BindView(R.id.instruction_text)
    TextView            mTvInstructions;
    private boolean         mExoPlayerFullscreen = false;
    private SimpleExoPlayer mExoPlayer;
    private Step            mStep;
    private Unbinder        unbinder;
    private long            mCurrentPosition     = 0;
    private boolean         mPlayWhenReady       = true;


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

        boolean mIsTabletDevice = getResources().getBoolean(R.bool.isTabletDevice);
        boolean isLandscapeOrientation;

        int currentOrientation = getResources().getConfiguration().orientation;

        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            isLandscapeOrientation = true;
        } else {
            isLandscapeOrientation = false;
        }

        if (NetworkHelper.isInternetAvailable(Objects.requireNonNull(getActivity()))) {
            if (!TextUtils.isEmpty(mStep.getVideoURL())) {

                initializePlayer(Uri.parse(mStep.getVideoURL()));

                if (!mIsTabletDevice && isLandscapeOrientation) {
                    mInstructionsContainer.setVisibility(View.INVISIBLE);
                    mIvThumbnail.setVisibility(View.INVISIBLE);
                    mTvInstructions.setVisibility(View.INVISIBLE);
                    openFullscreenDialog();
                } else if (mExoPlayerFullscreen) {
                    closeFullscreenDialog();
                    mInstructionsContainer.setVisibility(View.VISIBLE);
                }
            } else {
                // Show InstructionsContainer because in case of phone landscape is hidden
                mInstructionsContainer.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getContext(), "No internet", Toast.LENGTH_LONG).show();
        }
    }

    private void openFullscreenDialog() {
        mExoPlayerView.bringToFront();
        mExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);

        mExoPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        mExoPlayerFullscreen = true;
    }

    private void closeFullscreenDialog() {
        mExoPlayerView.setSystemUiVisibility(SYSTEM_UI_FLAG_VISIBLE);
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
            // Create a default TrackSelector
            DefaultBandwidthMeter  bandwidthMeter             = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector          trackSelector              = new DefaultTrackSelector(videoTrackSelectionFactory);

            // Create the player
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            // Bind the player to the view.
            mExoPlayerView.setPlayer(mExoPlayer);
            // Measures bandwidth during playback. Can be null if not required.
            // Produces DataSource instances through which media data is loaded.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(Objects.requireNonNull(getContext()), Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidthMeter);
            // This is the MediaSource representing the media to be played.
            MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(mediaUri);
            // Prepare the player with the source.
            mExoPlayer.prepare(videoSource);

            // onRestore
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