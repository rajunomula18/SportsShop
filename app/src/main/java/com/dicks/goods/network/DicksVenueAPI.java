package com.dicks.goods.network;

import com.dicks.goods.model.VenueListModel;

import retrofit.Call;
import retrofit.http.GET;

public interface DicksVenueAPI {
    String BASE_URL = "https://movesync-qa.dcsg.com";

    @GET("/dsglabs/mobile/api/venue/")
    Call<VenueListModel> getDickVenues();
}
