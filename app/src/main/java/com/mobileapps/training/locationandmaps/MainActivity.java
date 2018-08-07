package com.mobileapps.training.locationandmaps;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements PermissionManager.Callback, LocationManager.Callback {

    private static final String TAG = "MainActivity";

    private LocationManager locationManager;
    private PermissionManager permissionManager;
    private TextView tvLat;
    private TextView tvLong;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvLat = findViewById(R.id.tvLat);
        tvLong = findViewById(R.id.tvLong);

        permissionManager = new PermissionManager(this);
        permissionManager.setPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        locationManager = new LocationManager(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        permissionManager.checkPermission();
    }

    @Override
    public void onPermissionResults(int requestCode, boolean isGranted) {
        if (isGranted) {
            locationManager.getCurrentLocation();
        }
    }

    @Override
    public void onLocationResults(Location location) {
        this.location = location;
        Log.d(TAG, "onLocationResults: " + location.toString());
        tvLat.setText(String.valueOf(location.getLatitude()));
        tvLong.setText(String.valueOf(location.getLongitude()));
    }

    public void onClickOpenMap(View view) {

        Log.d(TAG, "onClickOpenMap: ");
        Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
        intent.putExtra("location",location);
        startActivity(intent);
    }
}
