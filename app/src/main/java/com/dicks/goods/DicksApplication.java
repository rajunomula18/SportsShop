package com.dicks.goods;

import android.app.Application;

import com.dicks.goods.model.VenueListModel;

import java.util.List;



public class DicksApplication extends Application {
    private AppComponent dicksComponent;
    private List<VenueListModel.Venues> venueList;

    @Override
    public void onCreate() {
        super.onCreate();
        dicksComponent = DaggerAppComponent.builder()
                .dicksModule(new DicksModule(this))
                .build();
    }

    public AppComponent getDicksComponent() {
        return dicksComponent;
    }

    public void setVenueList(List<VenueListModel.Venues> venueList) {
        this.venueList = venueList;
    }

    public List<VenueListModel.Venues> getVenueList() {
        return venueList;
    }
}
