package com.dicks.goods.ui;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.dicks.goods.DicksApplication;
import com.dicks.goods.R;
import com.dicks.goods.model.VenueListModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements VenuesFragment.OnListFragmentInteractionListener {

    public static final String VENUE_DETAILS = "VENUE_DETAILS";
    private final int permissionRequestCode = 1;

    @Inject
    @Named("DicksModule")
    SharedPreferences pref;

    private FusedLocationProviderClient mFusedLocationClient;

    public Location getCurrentLocation() {
        return currentLocation;
    }

    private Location currentLocation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((DicksApplication) getApplicationContext()).getDicksComponent().inject(this);
        fetchCurrentLocation();
    }

    private void fetchCurrentLocation() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, permissionRequestCode);
            return;
        }
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            currentLocation = location;
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case permissionRequestCode:
                if (grantResults.length > 0
                        && (grantResults[0] == PackageManager.PERMISSION_GRANTED || grantResults[1] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(MainActivity.this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onListFragmentInteraction(VenueListModel.Venues item) {
        Intent intent = new Intent(MainActivity.this, DickDetailsActivity.class);
        intent.putExtra(VENUE_DETAILS, ((DicksApplication)getApplication()).getVenueList().indexOf(item));
        startActivity(intent);
    }
}
