package com.easyapps.locationapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    Initial initial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (hasPermission())
            initial = new Initial(this);
        else
            requestPermission();

    }

    private void requestPermission() {
        Log.i("flow", "requestPermission: ");
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                , 101);

    }

    private boolean hasPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
            return true;
        else
            return false;
    }

    private void permissionDeniedsnackBar() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.txt), "Location permission " + //you can place any view in first parameter
                "is required to get the latitude and longitude" +                               //it does't matter
                "", Snackbar.LENGTH_LONG);

        snackbar.setAction("ok", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();

            }
        });
        snackbar.setActionTextColor(Color.GREEN);
        View sb = snackbar.getView();
        TextView textView = (TextView) sb.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.CYAN);
        snackbar.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        Log.i("flow", "onRequestPermissionsResult: ");
        switch (requestCode) {
            case 101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Initial initial = new Initial(this);
                } else {
                    permissionDeniedsnackBar();
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            //add more cases if you are making more than one permission request
        }
    }

}
