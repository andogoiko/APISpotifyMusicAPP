package com.example.radiopatio;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.radiopatio.R;
import com.example.radiopatio.databinding.ActivityMainBinding;
import com.example.radiopatio.models.Cancion;
import com.example.radiopatio.utility.SpotifyEndpoints;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

public class WorkingActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static String USER_TOKEN = "";
    private static SpotifyAppRemote mSpotifyAppRemote;
    private static SpotifyEndpoints spotifyEndpoints;
    private static Activity workingAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();

        ImageView finalPlayControl = (ImageView) findViewById(R.id.playControl);

        if (extras != null) {

            USER_TOKEN = extras.getString("token");

            mSpotifyAppRemote = LaunchActivity.getSpotifyAppRemote();
            //The key argument here must match that used in the other activity

            spotifyEndpoints = new SpotifyEndpoints(USER_TOKEN, this);

            LaunchActivity.killLaunch();

            workingAct = this;

            // Subscribe to PlayerState
            mSpotifyAppRemote.getPlayerApi()
                    .subscribeToPlayerState()
                    .setEventCallback(playerState -> {
                        final Track track = playerState.track;
                        if (track != null) {
                            //Log.d("MainActivity", track.name + " by " + track.artist.name);

                            TextView nameTrack = (TextView) findViewById(R.id.tvSongName);

                            TextView nameArtist = (TextView) findViewById(R.id.tvArtistName);

                            ImageView ivPortada = (ImageView) findViewById(R.id.iPlayingCaratula);

                            mSpotifyAppRemote.getImagesApi().getImage(track.imageUri).setResultCallback(
                                    new CallResult.ResultCallback<Bitmap>() {
                                        @Override public void onResult(Bitmap bitmap) {

                                            ivPortada.setImageBitmap(bitmap);

                                            nameTrack.setText(track.name);

                                            nameArtist.setText(track.artist.name);
                                        }
                                    });

                            if (playerState.isPaused){
                                finalPlayControl.setImageResource(R.drawable.play);
                            }else{
                                finalPlayControl.setImageResource(R.drawable.pause);
                            }
                        }
                    });
        }

        //mSpotifyAppRemote.getPlayerApi().play("spotify:track:2jnZUvhw2LTvDI6YidRVcO");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        LinearLayout player = (LinearLayout) findViewById(R.id.llPlayer);

        ImageView playControl = (ImageView) findViewById(R.id.playControl);

        player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentContainerView fcvNowPlay = (FragmentContainerView) findViewById(R.id.fragmentContainerView4);

                fcvNowPlay.setVisibility(View.VISIBLE);

                BottomNavigationView bnvMenu = (BottomNavigationView) findViewById(R.id.nav_view);

                bnvMenu.setVisibility(View.GONE);

                View fragmentNav = (View) findViewById(R.id.nav_host_fragment_activity_main);

                fragmentNav.setVisibility(View.GONE);

                LinearLayout llPlayer = (LinearLayout) findViewById(R.id.llPlayer);

                llPlayer.setVisibility(View.GONE);

            }
        });

        playControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mSpotifyAppRemote.getPlayerApi().getPlayerState()
                        .setResultCallback(playerState -> {
                            final Track track = playerState.track;

                            if (playerState.isPaused){

                                playControl.setImageResource(R.drawable.play);

                                mSpotifyAppRemote.getPlayerApi().resume();

                            }else{

                                playControl.setImageResource(R.drawable.pause);

                                mSpotifyAppRemote.getPlayerApi().pause();

                            }
                        }).setErrorCallback(throwable -> {
                            // =(
                        });


            }
        });



    }


    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

    }

    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public static String getUserToken() {
        return USER_TOKEN;
    }

    public static SpotifyEndpoints getSpotifyEndpoints() {
        return spotifyEndpoints;
    }

    public static SpotifyAppRemote getmSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    public static Activity getWorkingAct() {
        return workingAct;
    }
}