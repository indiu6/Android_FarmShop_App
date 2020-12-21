package com.example.myfarmshop;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchproductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchproductFragment extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String[] productList;
    private Spinner spnProduct;
    private String selectedProduct;

    public SearchproductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchproductFragment newInstance(String param1, String param2) {
        SearchproductFragment fragment = new SearchproductFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_searchproduct, container, false);

        //Get Product List
        DataBaseHelper dbh;
        dbh = new DataBaseHelper(getActivity());
        Cursor cursor;
        String productname;

        cursor =dbh.searchProduct();

        if(cursor.getCount() < 1){
            ((MainActivity)getActivity()).showMessage("No products", "No product records were found in database");
        }else {
            productList = new String[cursor.getCount()];
            if (cursor.moveToFirst()) {
                int i = 0;
                do {
                    productname = cursor.getString(cursor.getColumnIndex("name"));
                    productList[i] = productname;
                    i++;
                } while (cursor.moveToNext());
            }
            cursor.close();
            dbh.close();
            //bindAdapter();
        }
        spnProduct = (Spinner) v.findViewById(R.id.spnProduct);
        Button btnSearch = (Button) v.findViewById(R.id.btnSearch);
        selectedProduct = productList[0];
        ArrayAdapter<String> programCodeAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, productList);
        programCodeAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnProduct.setAdapter(programCodeAdapter);

        //product code select event
        spnProduct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //set selected size name
                selectedProduct = productList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectKey = "product";
                String selectValue = selectedProduct;
                Bundle bundle = new Bundle();


                //Set key and value for search SQL
                bundle.putString("key", selectKey);
                bundle.putString("value", selectValue);
                Fragment frag = new ViewFarmFragment();
                frag.setArguments(bundle);

                if (frag != null) {
                    //Set fragment to frame on main screen
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame, frag);
                    transaction.commit();
                }
            }

            });
        return v;
    }

}