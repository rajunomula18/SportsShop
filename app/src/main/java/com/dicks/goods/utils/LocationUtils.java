package com.dicks.goods.utils;

import android.location.Location;



public class LocationUtils {
    public static float getDistance(Location l1, Location l2){
        return l1.distanceTo(l2);
    }
}
