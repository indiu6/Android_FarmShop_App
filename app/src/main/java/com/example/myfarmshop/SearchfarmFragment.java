package com.example.myfarmshop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class SearchfarmFragment extends Fragment {

    //private RecyclerView recyclerView;
    //private RecyclerView.Adapter adapter;
    //private RecyclerView.LayoutManager layoutManager;
    //ArrayList<ModelFarms> data = new ArrayList<>();
    DataBaseHelper db;
    EditText search;
    Button btnSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_searchfarm, container, false);

        search = view.findViewById(R.id.search);
        btnSearch = view.findViewById(R.id.btnsearch);

        //recyclerView = view.findViewById(R.id.myrecicleview);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //recyclerView.setHasFixedSize(true);

        db = new DataBaseHelper(getContext());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectKey = "farm";
                String selectValue = search.getText().toString();
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
        return view;
    }
}

