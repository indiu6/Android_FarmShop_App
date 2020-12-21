package com.example.myfarmshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpecialoffersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpecialoffersFragment extends Fragment implements SpecialOfferListAdapter.OnSpecialOfferListener {

    View view;

    private RecyclerView recyclerView;
    //private ArrayList<SpecialOffer> specialOffers = new ArrayList<>();
    private SpecialOfferListAdapter adapter;

    DataBaseHelper dbh;  // Object to help with the DB operations

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private List<SpecialOffer> mList = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SpecialoffersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpecialoffersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpecialoffersFragment newInstance(String param1, String param2) {
        SpecialoffersFragment fragment = new SpecialoffersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // First, inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_specialoffers, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);  // Instantiate the recycler view

        // Instantiate the DatabaseHelper Object
        dbh = new DataBaseHelper(getActivity());
        Cursor cursor;
        Cursor farmCursor;
        cursor =dbh.searchSpecialOffer();
        if(cursor.getCount() < 1){
            ((MainActivity)getActivity()).showMessage("No special offers", "No special offers were found in database");
        }else{
            if(cursor.moveToFirst()){
                //Date date = new Date(System.currentTimeMillis());
                do{
                    farmCursor = dbh.searchFarm(cursor.getInt(cursor.getColumnIndex("farmId")));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); // Format to display the dates

                    SpecialOffer specialObj = new SpecialOffer();

                    specialObj.setFarmId(cursor.getInt(cursor.getColumnIndex("farmId")));
                    specialObj.setType(cursor.getInt(cursor.getColumnIndex("type")));
                    specialObj.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                    specialObj.setDateSince(new Date(cursor.getLong(cursor.getColumnIndex("dateSince"))));
                    specialObj.setDateUntil(new Date(cursor.getLong(cursor.getColumnIndex("dateUntil"))));
                    if(farmCursor.getCount() > 0){
                        specialObj.setFarmName(farmCursor.getString(farmCursor.getColumnIndex("name")));
                    }
                    specialObj.setFarmPicture((farmCursor.getBlob(farmCursor.getColumnIndex("picture"))));
                    specialObj.setFarmLatitude((farmCursor.getDouble((farmCursor.getColumnIndex("locLatiture")))));
                    specialObj.setFarmLongitude((farmCursor.getDouble((farmCursor.getColumnIndex("locLongitude")))));
                    //specialObj.setDateSince(date);
                    //specialObj.setDateUntil(date);
                    mList.add(specialObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbh.close();
            BindAdapter();
        }
        return view;
    }

    private void BindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager((layoutManager));

        adapter = new SpecialOfferListAdapter(mList, this.getContext(), this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSpecialOfferClick(int position) {

        SpecialOffer specialOffer = mList.get(position);

        // Create a new intent to show the selected farm in Google Maps and pass its farmId
        Intent intent = new Intent(this.getActivity(), MapsActivity.class);
        intent.putExtra("farmId", specialOffer.getFarmId());
        startActivity(intent);
    }
}