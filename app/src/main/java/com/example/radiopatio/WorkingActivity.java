package com.example.radiopatio;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
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
import com.spotify.protocol.types.Track;
import com.squareup.picasso.Picasso;

public class WorkingActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static String USER_TOKEN = "";
    private SpotifyAppRemote mSpotifyAppRemote;
    private static SpotifyEndpoints spotifyEndpoints;
    private Activity workingAct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
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

                            spotifyEndpoints.getCurrentTrack(track.name, track.artist.name, ivPortada, workingAct);

                            nameTrack.setText(track.name);

                            nameArtist.setText(track.artist.name);

                            //ivPortada.setImageURI();

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

}