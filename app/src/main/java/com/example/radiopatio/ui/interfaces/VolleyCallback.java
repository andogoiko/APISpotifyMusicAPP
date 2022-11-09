package com.example.radiopatio.ui.interfaces;

import org.json.JSONObject;

public interface VolleyCallback {
    JSONObject onSuccess(JSONObject result);
    void onError(String result);
}
