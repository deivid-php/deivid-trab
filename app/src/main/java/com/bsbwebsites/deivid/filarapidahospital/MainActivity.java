package com.bsbwebsites.deivid.filarapidahospital;

import android.location.Address;
import android.location.Geocoder;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.Locale;

public class MainActivity extends ActPrincipal implements View.OnClickListener {

    //private
    public static final int REQUEST_LOCATION = 1;
    Button button;
    TextView textView;
    LocationManager locationManager;
    String lattitude;
    String longitude;

    public static double lat;
    public static double lon;

    public static String localizar;

    protected double lattitudeB;
    protected double longtudeB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //início do chamamento do menu superior e lateral
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.activitylayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //fechamento do chamamento do menu superior e lateral


        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);


        textView = (TextView)findViewById(R.id.text_location);

        button = (Button)findViewById(R.id.button_location);

        button.setOnClickListener(this);

        localizar = getAddress(lat, lon);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
        }


    }

    @Override
    public void onClick(View view) {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            getLocation();
            String teste = getAddress(lat, lon);
            textView.setText("Sua localização é"+ "\n" + "Lattitude = " + teste);
            localizar = getAddress(lat, lon);
        }
    }
//MUDEI PARA PUBLIC
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            Location location1 = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            Location location2 = locationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);


            if (location != null) {
                double latti = location.getLatitude();
                double longi = location.getLongitude();
                lat = location.getLatitude();
                lon = location.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textView.setText("Sua localização é"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                localizar = getAddress(lat, lon);

            } else  if (location1 != null) {
                double latti = location1.getLatitude();
                double longi = location1.getLongitude();
                lat = location1.getLatitude();
                lon = location1.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textView.setText("Sua localização é"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                localizar = getAddress(lat, lon);


            } else  if (location2 != null) {
                double latti = location2.getLatitude();
                double longi = location2.getLongitude();
                lat = location2.getLatitude();
                lon = location2.getLongitude();
                lattitude = String.valueOf(latti);
                longitude = String.valueOf(longi);

                textView.setText("Sua localização é"+ "\n" + "Lattitude = " + lattitude
                        + "\n" + "Longitude = " + longitude);
                localizar = getAddress(lat, lon);


            }else{

                Toast.makeText(this,"não foi possível localizar",Toast.LENGTH_SHORT).show();

            }

        }
    }


    //PROTECTED
    public void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Favor ligue sua GPS Connection")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void startSecondActivity(View view) {

        Intent intent2 = new Intent(this,ActivityLogin.class);
        this.startActivity(intent2);
    }

    //metodo para pegar a lat e long e transformar em String
    private String getAddress(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("My Current address", strReturnedAddress.toString());
            } else {
                Log.w("My Current address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current address", "Canont get Address!");
        }
        return strAdd;
    }


}