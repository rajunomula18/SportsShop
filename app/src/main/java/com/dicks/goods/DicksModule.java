package com.dicks.goods;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DicksModule {
    private DicksApplication dicksApplication;
    private SharedPreferences sharedPreferences;
    private String PREF_NAME = "DicksModule";

    public DicksModule(DicksApplication dicksApplication) {
        this.dicksApplication = dicksApplication;
    }

    @Provides
    @Singleton
    @Named("DicksModule")
    public SharedPreferences provideSharedPref() {
        return sharedPreferences = dicksApplication.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    public DicksApplication provideApplication() {
        return dicksApplication;
    }
}
