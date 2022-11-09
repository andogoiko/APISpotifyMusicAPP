package com.example.radiopatio.utility;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Spotify {

    private RequestQueue rq;
    private String USER_TOKEN;
    private Activity activity;

    public Spotify(String USER_TOKEN, Activity activity){
        this.USER_TOKEN = USER_TOKEN;
        this.activity = activity;
        rq = Volley.newRequestQueue(activity);
        Log.i("TokenSp", USER_TOKEN);
    }

    public void getDevices(){

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
                    posTrack.put("uri", "spotify:track:1Ne8Ty61mf4VUwXvbwC1l8");

                    jsonBody.put("context_uri", "spotify:album:6cGNmvcaZLZWdGQHcpNXOe");
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
}
