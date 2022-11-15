package com.example.radiopatio.ui.buscador;

import static com.example.radiopatio.WorkingActivity.getUserToken;

import android.app.Activity;
import android.view.View;
import android.widget.SearchView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radiopatio.R;
import com.example.radiopatio.adapters.TrackAdapter;
import com.example.radiopatio.models.Cancion;
import com.example.radiopatio.utility.SpotifyEndpoints;

import java.util.ArrayList;

public class BuscadorViewModel extends ViewModel implements SearchView.OnQueryTextListener{

    private SearchView buscadol;
    private SpotifyEndpoints spotify;
    private View root;

    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static ArrayList<Cancion> trackList;

    private final MutableLiveData<String> mText;

    public BuscadorViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void fragmentCharge(View root){

        this.root = root;

        spotify = new SpotifyEndpoints(getUserToken(), (Activity) root.getContext());

        buscadol = root.findViewById(R.id.buscador);

        buscadol.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        spotify.searchSongs(s);

        setSearchRV();

        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() == 0 || s == null){
            trackList = new ArrayList<>();
            setSearchRV();
        }
        return false;
    }

    private void setSearchRV(){
        recyclerView = root.findViewById(R.id.trackResult);
        trackAdapter = new TrackAdapter(root.getContext(), trackList);
        linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackAdapter);
    }


}