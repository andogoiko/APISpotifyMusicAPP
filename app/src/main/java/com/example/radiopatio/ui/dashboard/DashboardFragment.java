package com.example.radiopatio.ui.dashboard;

import static com.example.radiopatio.WorkingActivity.getUserToken;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.radiopatio.R;
import com.example.radiopatio.databinding.FragmentDashboardBinding;
import com.example.radiopatio.utility.Spotify;

public class DashboardFragment extends Fragment implements SearchView.OnQueryTextListener{

    private FragmentDashboardBinding binding;
    private SearchView buscadol;
    private Spotify spotify;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        spotify = new Spotify(getUserToken(), getActivity());

        buscadol = root.findViewById(R.id.buscador);

        buscadol.setOnQueryTextListener(this);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        spotify.getDevices();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        Log.i("escroto", s);
        return false;
    }
}