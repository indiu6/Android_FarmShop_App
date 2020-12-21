package com.example.myfarmshop;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewFarmFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewFarmFragment extends Fragment implements FarmListAdapter.OnFarmListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView mRecyclerview;
    private List<Farm> mList = new ArrayList<>();
    private FarmListAdapter mAdapter;
    DataBaseHelper dbh;

    public ViewFarmFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewFarmFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewFarmFragment newInstance(String param1, String param2) {
        ViewFarmFragment fragment = new ViewFarmFragment();
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
        View v = inflater.inflate(R.layout.fragment_view_farm, container, false);
        mRecyclerview = (RecyclerView) v.findViewById(R.id.recyclerView);
        dbh = new DataBaseHelper(getActivity());
        Cursor cursor;
        String myKey = null;
        String myValue = null;
        if(getArguments() != null){
            myKey = this.getArguments().getString("key");
            myValue = this.getArguments().getString("value");
        }
        if(myKey != null && myValue != null){
            cursor =dbh.viewData(myKey, myValue);
        }else{
            cursor =dbh.searchFarm();
        }

        if(cursor.getCount() < 1){
            Toast.makeText(getContext(), "There is No Record", Toast.LENGTH_LONG).show();
        }else{
            if(cursor.moveToFirst()){
                do{
                    Farm farmObj = new Farm();
                    farmObj.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    farmObj.setName(cursor.getString(cursor.getColumnIndex("name")));
                    farmObj.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                    farmObj.setCity(cursor.getString(cursor.getColumnIndex("city")));
                    farmObj.setProvince(cursor.getString(cursor.getColumnIndex("province")));
                    farmObj.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
                    farmObj.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                    farmObj.setPicture( cursor.getBlob(cursor.getColumnIndex("picture")));
                    mList.add(farmObj);
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbh.close();
            bindAdapter();
        }
        return v;
    }

    private void bindAdapter(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(layoutManager);
        mAdapter = new FarmListAdapter(mList, getContext(), this);
        mRecyclerview.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFarmClick(int position) {
        Farm selectedFarm = mList.get(position);

        // Create a new intent to show the selected farm in Google Maps and pass its farmId
        Intent intent = new Intent(this.getActivity(), MapsActivity.class);
        intent.putExtra("farmId", selectedFarm.getId());
        startActivity(intent);
    }
}