package com.example.tp_synthese.api;

import com.example.tp_synthese.models.BrainShopResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface BrainShopApi {
    @GET
    Call<BrainShopResponse> getMessage(@Url String url);
}
