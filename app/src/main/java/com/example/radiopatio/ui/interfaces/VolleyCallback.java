package com.example.radiopatio.ui.interfaces;

import com.example.radiopatio.models.Cancion;

import java.util.ArrayList;

public interface VolleyCallback {
    ArrayList<Cancion> onSuccess(String  result);
}
