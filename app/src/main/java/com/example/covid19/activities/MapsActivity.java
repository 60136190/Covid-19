package com.example.covid19.activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.covid19.R;
import com.example.covid19.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng hanoi = new LatLng(21.018679656776833, 105.85550768002875);
        mMap.addMarker(new MarkerOptions().position(hanoi).title("Bệnh viện đa khoa Hà Nội"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hanoi));

        LatLng danang = new LatLng(16.07255026822152, 108.21560132861244);
        mMap.addMarker(new MarkerOptions().position(danang).title("Bệnh viện đa khoa Đà Nẵng"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(danang));

        LatLng nhatrang = new LatLng(12.250610054073988, 109.19171610972697);
        mMap.addMarker(new MarkerOptions().position(nhatrang).title("Bệnh viện đa Nha Trang"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(nhatrang));

        LatLng saigon = new LatLng(10.772166271543805, 106.69938571832988);
        mMap.addMarker(new MarkerOptions().position(saigon).title("Bệnh viện đa khoa Sài Gòn"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(saigon));
    }
}