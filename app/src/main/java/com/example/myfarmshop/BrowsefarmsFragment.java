package com.example.myfarmshop;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class BrowsefarmsFragment extends Fragment
        implements OnMapReadyCallback {

    private MapView mapView = null;

    private List<Farm> farmList = new ArrayList<>();
    DataBaseHelper dbh;  // Object to help with the DB operations
    Cursor cursor;

    private GoogleMap mMap;

    public BrowsefarmsFragment()
    {
        // required constructor
    }

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add markers to the map.
        addMarkersToMap();

        // Setting an info window adapter allows us to change the both the contents and look of the info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        // set the start position of map
        LatLng startFarmPosition = new LatLng(farmList.get(1).getLatitude(), farmList.get(1).getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLng(startFarmPosition));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_maps, container, false);

        mapView = (MapView) view.findViewById(R.id.map);
        mapView.getMapAsync(this);

        // Instantiate the DatabaseHelper Object
        dbh = new DataBaseHelper(getActivity());
        cursor = dbh.searchFarm();

        if (cursor.getCount() < 1){
            ((MainActivity)getActivity()).showMessage("No Farm data", "No Farm Data found in database");
            return view;
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
                    farmObj.setUrl((cursor.getString(cursor.getColumnIndex("url"))));
                    farmObj.setLatitude((cursor.getDouble(cursor.getColumnIndex("locLatiture"))));
                    farmObj.setLongitude((cursor.getDouble(cursor.getColumnIndex("locLongitude"))));
                    farmList.add(farmObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbh.close();
        }

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // some method when Activity is initialized

        if(mapView != null)
            mapView.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
}