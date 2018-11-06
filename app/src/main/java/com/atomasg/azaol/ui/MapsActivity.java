package com.atomasg.azaol.ui;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.atomasg.azaol.R;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private final double INITIAL_LATITUDE = 40.246232;
    private final double INITIAL_LONGITUDE = -6.276418;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMinZoomPreference(8.0f);



        LatLng palomero = new LatLng(INITIAL_LATITUDE, INITIAL_LONGITUDE);
        mMap.addMarker(new MarkerOptions().position(palomero).title("Marcador Inicial"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(palomero));
    }

    @OnClick(R.id.btInitialMarker)
    public void onInitialMarterClick() {
        LatLng initial = new LatLng(INITIAL_LATITUDE, INITIAL_LONGITUDE);
        mMap.addMarker(new MarkerOptions().position(initial).title("Marcador Inicial"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initial, 15.0f));
    }
}
