package com.example.radiopatio.models;

public class Album {

    private String nomAlbum;
    private String nomArtista;
    private String albumUri;
    private String externalURL;
    private String albumImgURL;

    public Album(String nomAlbum, String nomArtista, String albumUri, String externalURL, String albumImgURL) {
        this.nomAlbum = nomAlbum;
        this.albumUri = albumUri;
        this.externalURL = externalURL;
        this.albumImgURL = albumImgURL;
        this.nomArtista = nomArtista;
    }
    public Album(){}

    public String getNomAlbum() {
        return nomAlbum;
    }

    public void setNomAlbum(String nomAlbum) {
        this.nomAlbum = nomAlbum;
    }

    public String getNomArtista() {
        return nomArtista;
    }

    public void setNomArtista(String nomArtista) {
        this.nomArtista = nomArtista;
    }

    public String getAlbumUri() {
        return albumUri;
    }

    public void setAlbumUri(String albumUri) {
        this.albumUri = albumUri;
    }

    public String getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(String externalURL) {
        this.externalURL = externalURL;
    }

    public String getAlbumImgURL() {
        return albumImgURL;
    }

    public void setAlbumImgURL(String albumImgURL) {
        this.albumImgURL = albumImgURL;
    }
}
