package com.dicks.goods;

import com.dicks.goods.ui.DickDetailsActivity;
import com.dicks.goods.ui.MainActivity;
import com.dicks.goods.ui.VenuesFragment;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {DicksModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
    void inject(VenuesFragment venuesFragment);
    void inject(DickDetailsActivity dickDetailsActivity);
}
