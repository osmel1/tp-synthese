package com.example.tp_synthese.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable
{
    @SerializedName("volumeInfo")
    private VolumeInfo volumeInfo;

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public Book(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }
}
