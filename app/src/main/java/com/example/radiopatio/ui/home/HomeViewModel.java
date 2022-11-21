package com.example.radiopatio.ui.home;

import static com.example.radiopatio.WorkingActivity.getUserToken;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
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
    private static View root;

    private static RecyclerView recyclerView;
    private static TrackAdapter trackAdapter;
    private static LinearLayoutManager linearLayoutManager;

    public static ArrayList<Cancion> last10Heard;

    private final MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("This is home fragment");
    }

    public void HomeViewModelCharge(View root){

        this.root = root;

        spotify = new SpotifyEndpoints(getUserToken(), (Activity) root.getContext());

        // llama al endpoint de recentlyplayed para recoger las últimas 10 canciones que has escuchado y las colocarla en el recyclerview de las últimas 10 escuchadas

        spotify.recentlyPlayed();

        chargeListRecentlyPlayed();

    }


    public LiveData<String> getText() {
        return mText;
    }

    public static void chargeListRecentlyPlayed(){

        //setea la lista que alienta la llamada a la api al recyclerview

        recyclerView = root.findViewById(R.id.rvLastHeard);
        trackAdapter = new TrackAdapter(root.getContext(), last10Heard);
        linearLayoutManager = new LinearLayoutManager(root.getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(trackAdapter);
    }
}