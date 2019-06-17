package com.geekbrains.a1l5_fragments.tools;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.geekbrains.a1l5_fragments.MainActivity;


public class GeoLocation {
    private MainActivity mainActivity;

    public GeoLocation(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public void FindLocation(Boolean isPermOn){
        if(!isPermOn){
            if(isPermisionEnable()){
                requestLocation();
            }else{
                requestLocationPermissions();
            }
        }
        else{
            requestLocation();
        }

    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        LocationManager locationManager = (LocationManager) mainActivity.getSystemService(Context.LOCATION_SERVICE);
        assert locationManager != null;
        Location location;
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Geocoder geocoder = new Geocoder(mainActivity);
        Address address;
        String nameCity="";
        try {
            address = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1).get(0);
            nameCity = address.getLocality();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainActivity.addCity(nameCity,location.getLatitude(),location.getLongitude());
    }

    private Boolean isPermisionEnable(){
        return  (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED)
                && (ActivityCompat.checkSelfPermission(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED);
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(mainActivity,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },mainActivity.PERMISSION_REQUEST_CODE);
    }

}
