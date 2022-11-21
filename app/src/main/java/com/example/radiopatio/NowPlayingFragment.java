package com.example.radiopatio;

import static com.example.radiopatio.WorkingActivity.getWorkingAct;
import static com.example.radiopatio.WorkingActivity.getmSpotifyAppRemote;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.android.appremote.api.AppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.Track;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NowPlayingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NowPlayingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
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

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);

        ImageView ivBack = (ImageView) view.findViewById(R.id.ivBack);

        // "minimiza" la info de la canción actua y vuelve a mostrar el main

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity mainAct = getWorkingAct();

                FragmentContainerView fcvNowPlay = (FragmentContainerView) mainAct.findViewById(R.id.fragmentContainerView4);

                fcvNowPlay.setVisibility(View.GONE);

                BottomNavigationView bnvMenu = (BottomNavigationView) mainAct.findViewById(R.id.nav_view);

                bnvMenu.setVisibility(View.VISIBLE);

                View fragmentNav = (View) mainAct.findViewById(R.id.nav_host_fragment_activity_main);

                fragmentNav.setVisibility(View.VISIBLE);

                LinearLayout llPlayer = (LinearLayout) mainAct.findViewById(R.id.llPlayer);

                llPlayer.setVisibility(View.VISIBLE);

            }
        });


        ImageView ivPlay = (ImageView) view.findViewById(R.id.ivPlay);

        ImageView ivCaratula = (ImageView) view.findViewById(R.id.ivCaratula);

        TextView ivTrack = (TextView) view.findViewById(R.id.tvTrackNameNow);

        TextView ivArtista = (TextView) view.findViewById(R.id.tvTrackArtistNameNow);

        // actualiza en vivo la canción que suena

        AppRemote mSpotifyAppRemote = getmSpotifyAppRemote();

        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;

                    mSpotifyAppRemote.getImagesApi().getImage(track.imageUri).setResultCallback(
                            new CallResult.ResultCallback<Bitmap>() {
                                @Override public void onResult(Bitmap bitmap) {

                                    ivCaratula.setImageBitmap(bitmap);

                                    ivTrack.setText(track.name);

                                    ivArtista.setText(track.artist.name);
                                }
                            });

                    if (playerState.isPaused){
                        ivPlay.setImageResource(R.drawable.play);
                    }else{
                        ivPlay.setImageResource(R.drawable.pause);
                    }



                }).setErrorCallback(throwable -> {
                    // =(
                });

        // controla el play y pause

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track = playerState.track;

                            if (playerState.isPaused){

                                mSpotifyAppRemote.getPlayerApi().resume();

                            }else{

                                mSpotifyAppRemote.getPlayerApi().pause();

                            }
                        }).setErrorCallback(throwable -> {
                            // =(
                        });

            }
        });

        // siguiente canción

        ImageView ivNext = (ImageView) view.findViewById(R.id.ivNext);

        ivNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().skipNext();

            }
        });

        //anterior canción

        ImageView ivPrev = (ImageView) view.findViewById(R.id.ivPrev);

        ivPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().skipPrevious();

            }
        });

        return view;
    }


}