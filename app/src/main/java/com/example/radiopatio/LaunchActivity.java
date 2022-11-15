package com.example.radiopatio;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.radiopatio.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class LaunchActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "1e73724c394f4334af581e8eb6b5a797";
    private static final String CLIENT_SECRET = "4c92b131876b400db1116e8332fb14d1";
    private static final int REQUEST_CODE = 1337;
    private static final String REDIRECT_URI = "http://localhost/";
    private static String USER_TOKEN = "";
    private static SpotifyAppRemote mSpotifyAppRemote;
    private static Activity launchAct;
    private Intent i;
    /*
    * usuario genérico: ikbdy@plaiaundi.net
    * contraseña genérica: ikbdy2dam3
    * */

    public static SpotifyAppRemote getSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }

    public static void killLaunch(){
        launchAct.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        launchAct = this;
    }

    @Override
    protected void onStart() {
        super.onStart();

        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"});
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);

    }

    private void connect() {

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {

                        mSpotifyAppRemote = spotifyAppRemote;

                        startActivity(i);
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here

                        retryConnexion();

                    }
                });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                // Response was successful and contains auth token
                case TOKEN:
                    //Log.i("TOKEN", response.getAccessToken());
                    USER_TOKEN = "Bearer " + response.getAccessToken();

                    //intent con parámetros para pasar a la main activity (este seria el login digamos)

                    i = new Intent(LaunchActivity.this, WorkingActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("token", USER_TOKEN);

                    connect();

                    break;

                // Auth flow returned an error
                case ERROR:
                    retryConnexion();

                    Log.i("Error", "Ha ocurrido un error al recibir el token");
                    break;

                // Most likely auth flow was cancelled
                default:
                    retryConnexion();
                    Log.i("ErrorDEF", "NOT FOUND");
                    break;
            }
        }
    }

    public void retryConnexion(){
        new AlertDialog.Builder(LaunchActivity.this)
                .setTitle("Error de conexión")
                .setMessage("¿Quiere reintentar la conexión?")

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        launchAct.recreate();
                    }
                })
                /*
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)*/
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}