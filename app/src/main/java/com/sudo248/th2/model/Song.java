package com.sudo248.th2.model;

import java.io.Serializable;

public class Song implements Serializable {
    private int songId;
    private String songName;
    private Singer singer;
    private String album;
    private String type;
    private boolean isLike;

    public Song() {
    }

    public Song(String songName, Singer singer, String album, String type, boolean isLike) {
        this.songName = songName;
        this.singer = singer;
        this.album = album;
        this.type = type;
        this.isLike = isLike;
    }

    public Song(int songId, String songName, Singer singer, String album, String type, boolean isLike) {
        this.songId = songId;
        this.songName = songName;
        this.singer = singer;
        this.album = album;
        this.type = type;
        this.isLike = isLike;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }
}
