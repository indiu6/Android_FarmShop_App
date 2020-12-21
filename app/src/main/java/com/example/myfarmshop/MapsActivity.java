package com.example.myfarmshop;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<Farm> farmList = new ArrayList<>();
    DataBaseHelper dbh;  // Object to help with the DB operations
    Cursor cursor;

    private GoogleMap mMap;

    /** Demonstrates customizing the info window and/or its contents. */
    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        // This view is containing two TextViews with id "title" and "snippet".
        private final View mContents;

        CustomInfoWindowAdapter() {
            mContents = getLayoutInflater().inflate(R.layout.custom_info_contents, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {

            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }

            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null && snippet.length() > 0) {
                SpannableString snippetText = new SpannableString(snippet);
                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 0, snippet.length(), 0);
                snippetUi.setText(snippetText);
            } else {
                snippetUi.setText("");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map2);
        mapFragment.getMapAsync(this);

        // Instantiate the DatabaseHelper Object
        dbh = new DataBaseHelper(this.getApplicationContext());
        cursor = dbh.searchFarm();  // Get all farms from database

        // Create a list with the farms read from database
        if (cursor.getCount() < 1){
            //((MainActivity)getActivity()).showMessage("No Farm data", "No Farm Data found in database");
        } else {
            if(cursor.moveToFirst()){
                do {
                    Farm farmObj = new Farm();
                    farmObj.setId((cursor.getInt(cursor.getColumnIndex("id"))));
                    farmObj.setName((cursor.getString(cursor.getColumnIndex("name"))));
                    farmObj.setAddress((cursor.getString(cursor.getColumnIndex("address"))));
                    farmObj.setCity((cursor.getString(cursor.getColumnIndex("city"))));
                    farmObj.setProvince((cursor.getString(cursor.getColumnIndex("province"))));
                    farmObj.setPhone((cursor.getString(cursor.getColumnIndex("phone"))));
                    if (farmObj.getPhone() == null)
                        farmObj.setPhone("No phone number");
                    farmObj.setUrl((cursor.getString(cursor.getColumnIndex("url"))));
                    farmObj.setLatitude((cursor.getDouble(cursor.getColumnIndex("locLatiture"))));
                    farmObj.setLongitude((cursor.getDouble(cursor.getColumnIndex("locLongitude"))));
                    farmList.add(farmObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbh.close();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers to the map.
        addMarkersToMap();

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // Get farmId data from calling fragment
        Intent intent = getIntent();
        int farmId = intent.getIntExtra("farmId", 0);

        // Find the selected farm from farmList using farmId
        Farm selectedFarm = null;
        for (int i=0; i<farmList.size(); i++)
            if (farmList.get(i).getId() == farmId) {
                selectedFarm = farmList.get(i);
                break;
            }

        assert selectedFarm != null;
        LatLng selectedFarmLocation = new LatLng(selectedFarm.getLatitude(), selectedFarm.getLongitude());

        if (intent != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(selectedFarmLocation));
            Toast.makeText(getApplicationContext(), selectedFarm.getName() + " is selected", Toast.LENGTH_LONG).show();
        } else {
            ((MainActivity)getApplicationContext()).showMessage("Error", "No Map Data for Selected Farm");
        }

        // set the default start position of map
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(orchardFarm));
    }

    private void addMarkersToMap() {

        // Dynamically add all the markers in the map based on farmList table

        for (Farm farmToDisplay : farmList) {
            // Build string to display data of marker in map
            StringBuilder sb = new StringBuilder();
            sb.append(farmToDisplay.getAddress() + ", ");
            sb.append(farmToDisplay.getCity() + ", ");
            sb.append(farmToDisplay.getProvince());
            sb.append(System.getProperty("line.separator"));
            sb.append(farmToDisplay.getPhone());
            sb.append(System.getProperty("line.separator"));
            sb.append(farmToDisplay.getUrl());

            // Create farm coordinates object
            LatLng farmPosition = new LatLng(farmToDisplay.getLatitude(), farmToDisplay.getLongitude());

            // Add corresponding marker
            Marker farmMarker = mMap.addMarker(new MarkerOptions()
                    .position(farmPosition)
                    .title(farmToDisplay.getName())
                    .snippet(sb.toString()));
            farmMarker.showInfoWindow();
        }
    }
}