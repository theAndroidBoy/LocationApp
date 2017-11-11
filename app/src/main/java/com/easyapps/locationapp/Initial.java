package com.easyapps.locationapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.TextView;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class Initial implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private TextView txtOutput;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private Activity mActivity;

    Context context;

    public Initial(Activity activity) {
        Log.i("flow", "Initial: ");
        mActivity = activity;
        context = (Context) activity;
        initialSetup();
    }

    private void initialSetup() {
        Log.i("flow", "initialSetup: ");
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        txtOutput = (TextView) (mActivity.findViewById(R.id.txt));

        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionSuspended(int i) {

        Log.i("flow", "onConnectionSuspended: ");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("flow", "onConnectionFailed: ");
    }

    @Override
    public void onLocationChanged(Location location) {

        Log.i("flow", "onLocationChanged: " + Double.toString(location.getLatitude()));

        txtOutput.setText("latitude : " + Double.toString(location.getLatitude()) + "\n" +
                "longitude: " + Double.toString(location.getLongitude()));

    }

    @Override
    public void onConnected(Bundle bundle) {

        Log.i("flow", "onConnected: ");
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1000); // Update location every second

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            return;
        } else
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }


}
