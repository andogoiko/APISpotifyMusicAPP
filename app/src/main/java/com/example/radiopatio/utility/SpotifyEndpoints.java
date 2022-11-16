package com.example.radiopatio.utility;


import static com.example.radiopatio.ui.buscador.BuscadorViewModel.trackList;

import android.app.Activity;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.radiopatio.adapters.TrackAdapter;
import com.example.radiopatio.models.Cancion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SpotifyEndpoints {

    private RequestQueue rq;
    private String USER_TOKEN;
    private Activity activity;

    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private LinearLayoutManager linearLayoutManager;

    public SpotifyEndpoints(String USER_TOKEN, Activity activity){
        this.USER_TOKEN = USER_TOKEN;
        this.activity = activity;
        rq = Volley.newRequestQueue(activity);
    }

    public void getDevices(){



    }

    public void playTrack(String uriTrack, String uriAlbum){

        String endpoint = "https://api.spotify.com/v1/me/player/play";

        JsonObjectRequest devicesResquest = new JsonObjectRequest(Request.Method.PUT, endpoint, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //haz argo illo

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", USER_TOKEN);
                params.put("User-Agent", "Mozilla/5.0");

                return params;
            }

                @Override
                public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

                @Override
                public byte[] getBody() {
                    try {

                    JSONObject jsonBody = new JSONObject();

                    JSONObject posTrack = new JSONObject();
                    posTrack.put("uri", uriTrack);

                    jsonBody.put("context_uri", uriAlbum);
                    jsonBody.put("offset", posTrack);
                    jsonBody.put("position_ms", 0);

                    return jsonBody.toString().getBytes("utf-8");

                    } catch (JSONException e) {
                        Log.i("malJSON", "todo mal");
                        return null;
                    } catch (UnsupportedEncodingException e) {
                        Log.i("malENCODE", "todo mal");
                        return null;
                    }
                }
        };

        rq.add(devicesResquest);

    }

    public void stopTrack(){
        //https://developer.spotify.com/console/put-pause/
    }

    public void addToPLayer(){
        //https://developer.spotify.com/console/post-queue/
    }

    public void skipToPrev(){
        //https://developer.spotify.com/console/post-previous/
    }

    public void skipToNext(){
        //https://developer.spotify.com/console/post-next/
    }

    public void searchSongs(String songName){

        final String[] rawRes = new String[1];

        ArrayList<Cancion> canciones = new ArrayList<>();

        songName = songName.replaceAll("\\s", "%20");

        String endpoint = "https://api.spotify.com/v1/search?q=" + songName + "&type=track&limit=10";

        JsonObjectRequest devicesResquest = new JsonObjectRequest(Request.Method.GET, endpoint, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        trackList = new ArrayList<Cancion>();

                        JSONArray tracks = null;

                        Cancion srchdTrack;

                        try {

                            tracks = response.getJSONObject("tracks").getJSONArray("items");

                            for (int i = 0; i < tracks.length(); i++) {
                                JSONObject row = tracks.getJSONObject(i);
                                Log.i("track", row.toString());
                                //.i("artist", row.getJSONObject("album").getJSONArray("artists").getJSONObject(0).getString("name"));
                                //Log.i("albumName", row.getJSONObject("album").getString("name"));
                                //Log.i("albumURI", row.getJSONObject("album").getString("uri"));
                                //Log.i("imgURL", row.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url"));
                                //Log.i("trackName", row.getString("name"));
                                //Log.i("trackURI", row.getString("uri"));
                                //Log.i("trackURL", row.getJSONObject("external_urls").getString("spotify"));
                                //Log.i("albumkURL", row.getJSONObject("album").getJSONObject("external_urls").getString("spotify"));

                                srchdTrack = new Cancion(
                                        row.getJSONObject("album").getString("name"),
                                        row.getJSONObject("album").getJSONArray("artists").getJSONObject(0).getString("name"),
                                        row.getJSONObject("album").getString("uri"),
                                        row.getJSONObject("album").getJSONObject("external_urls").getString("spotify"),
                                        row.getJSONObject("album").getJSONArray("images").getJSONObject(0).getString("url"),
                                        row.getString("name"),
                                        row.getString("uri"),
                                        row.getJSONObject("external_urls").getString("spotify"));

                                trackList.add(srchdTrack);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                params.put("Content-Type", "application/json");
                params.put("Authorization", USER_TOKEN);
                params.put("User-Agent", "Mozilla/5.0");

                return params;
            }
        };

        rq.add(devicesResquest);


    }

    public void getYourTop10Artists(){
        //https://developer.spotify.com/console/get-current-user-top-artists-and-tracks/?type=artists&time_range=&limit=10&offset=
    }

    public void getYourPLaylists(){
        //https://developer.spotify.com/console/get-current-user-playlists/
    }

    public void createPlaylist(){
        //https://developer.spotify.com/console/post-playlists/
    }

    public void deleteFromPlaylist(){
        //https://developer.spotify.com/console/delete-playlist-tracks/
    }

    public void addToPlaylist(){
        //https://developer.spotify.com/console/post-playlist-tracks/
    }

    public void recentlyPlayed(){
        //https://developer.spotify.com/console/get-recently-played/?limit=&after=&before=
    }

    public void setVolume(){
        //https://developer.spotify.com/console/put-volume/
    }

}
