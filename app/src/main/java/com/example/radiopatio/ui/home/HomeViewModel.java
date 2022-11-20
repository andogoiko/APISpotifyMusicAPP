package com.example.radiopatio.ui.home;

import static com.example.radiopatio.WorkingActivity.getUserToken;

import android.app.Activity;
import android.view.View;

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

public class HomeViewModel extends ViewModel {

    private SpotifyEndpoints spotify;
    private View root;

    private RecyclerView recyclerView;
    private TrackAdapter trackAdapter;
    private LinearLayoutManager linearLayoutManager;

    public static ArrayList<Cancion> last10Heard;

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is home fragment");
    }

    public void HomeViewModelCharge(View root){

        this.root = root;

        spotify = new SpotifyEndpoints(getUserToken(), (Activity) root.getContext());

        spotify.recentlyPlayed();

        recyclerView = root.findViewById(R.id.rvLastHeard);
        trackAdapter = new TrackAdapter(root.getContext(), last10Heard);
        linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackAdapter);

    }


    public LiveData<String> getText() {
        return mText;
    }
}