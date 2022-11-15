package com.example.radiopatio.adapters;

import static com.example.radiopatio.WorkingActivity.getSpotifyEndpoints;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radiopatio.R;
import com.example.radiopatio.models.Cancion;
import com.example.radiopatio.utility.SpotifyEndpoints;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder>{

    private final Context context;
    private List<Cancion> trackList;
    private SpotifyEndpoints spotifyEndpoints = getSpotifyEndpoints();

    public TrackAdapter(Context context, ArrayList<Cancion> trackList) {
        this.context = context;
        this.trackList = trackList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_track_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cancion model = trackList.get(position);

        holder.trackName.setText(model.getNomTrack());

        holder.artistName.setText(model.getNomArtista());

        Picasso.get().load(model.getAlbumImgURL()).into(holder.imageView);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spotifyEndpoints.playTrack();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (trackList == null){
            return 0;
        }
        return trackList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView trackName;
        private final TextView artistName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.trackImage);
            trackName = itemView.findViewById(R.id.trackName);
            artistName = itemView.findViewById(R.id.trackArtist);
        }
    }
}
