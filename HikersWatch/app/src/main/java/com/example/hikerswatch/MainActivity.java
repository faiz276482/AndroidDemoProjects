package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    LocationManager locationManager;
    LocationListener locationListener;
    TextView latitudeTextView;
    TextView longitudeTextView;
    TextView accuracyTextView;
    TextView altitudeTextView;
    TextView addressTextView;
    TextView TextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        latitudeTextView=findViewById(R.id.latitudeTextView);
        longitudeTextView=findViewById(R.id.longitudeTextView);
        accuracyTextView=findViewById(R.id.accuracyTextView);
        altitudeTextView=findViewById(R.id.altitudeTextView);
        addressTextView=findViewById(R.id.addressTextView);


        locationManager=(LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //Log.i("Location: ", location.toString());
                updateLocationInfo(location);

            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
        else
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            Location lastKnownLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if(lastKnownLocation!=null)
            {
                updateLocationInfo(lastKnownLocation);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]==PackageManager.PERMISSION_GRANTED && grantResults.length>0)
        {
           startListening();;
        }
    }

    public  void startListening()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }

    public void updateLocationInfo(Location location)
    {
       // Log.i("Location",location.toString());
        longitudeTextView.setText("Longitude: "+location.getLongitude());
        latitudeTextView.setText("Latitude: "+location.getLatitude());
        accuracyTextView.setText("Accuracy: "+location.getAccuracy());
        altitudeTextView.setText("Altitude: "+location.getAltitude());

        String address="Could not find address";

        Geocoder geocoder=new Geocoder(this, getResources().getConfiguration().locale);
        try {
            List<Address> listAdresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            if(listAdresses.size()>0 && listAdresses != null)
            {
                address="Address:\n";

                if(listAdresses.get(0).getThoroughfare()!=null)
                {
                    address+=listAdresses.get(0).getThoroughfare()+"\n";
                }
                if(listAdresses.get(0).getLocality()!=null)
                {
                    address+=listAdresses.get(0).getLocality()+"\n";
                }
                if(listAdresses.get(0).getPostalCode()!=null)
                {
                    address+=listAdresses.get(0).getPostalCode()+"\n";
                }
                if(listAdresses.get(0).getAdminArea()!=null)
                {
                    address+=listAdresses.get(0).getAdminArea()+"\n";
                }
                addressTextView.setText(address);

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
