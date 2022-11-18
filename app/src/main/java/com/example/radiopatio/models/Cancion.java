package com.example.radiopatio.models;

public class Cancion extends Album{

    private String nomTrack;
    private String trackUri;
    private String trackExternalURL;

    public Cancion(String nomAlbum, String nomArtista, String albumUri, String externalURL, String albumImgURL, String nomTrack, String trackUri, String trackExternalURL) {
        super(nomAlbum, nomArtista, albumUri, externalURL, albumImgURL);
        this.nomTrack = nomTrack;
        this.trackUri = trackUri;
        this.trackExternalURL = trackExternalURL;;
    }

    public Cancion(){}

    public String getNomTrack() {
        return nomTrack;
    }

    public void setNomTrack(String nomTrack) {
        this.nomTrack = nomTrack;
    }

    public String getTrackUri() {
        return trackUri;
    }

    public void setTrackUri(String trackUri) {
        this.trackUri = trackUri;
    }

    public String getTrackExternalURL() {
        return trackExternalURL;
    }

    public void setTrackExternalURL(String trackExternalURL) {
        this.trackExternalURL = trackExternalURL;
    }

}
