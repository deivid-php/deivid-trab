package com.bsbwebsites.deivid.filarapidahospital;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.bsbwebsites.deivid.filarapidahospital.MainActivity.lat;
import static com.bsbwebsites.deivid.filarapidahospital.MainActivity.lon;

public class MapsFragment extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        mMap.getUiSettings().setZoomControlsEnabled(true);


        // Add a marker in Sydney and move the camera
        LatLng atual = new LatLng(lat, lon);
        mMap.addMarker(new MarkerOptions().position(atual).title("Minha Localização"));

        LatLng local1 = new LatLng(-16.02303876, -48.05966366);
        mMap.addMarker(new MarkerOptions().position(local1).title("Casa Caridade Gama"));

        LatLng local2 = new LatLng(-16.02996832, -48.02739132);
        mMap.addMarker(new MarkerOptions().position(local2).title("Caridade Santa Maria"));

        CameraPosition cameraPosition = new CameraPosition.Builder().zoom(12).target(atual).build();

        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

}
