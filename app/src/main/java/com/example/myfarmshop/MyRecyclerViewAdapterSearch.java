package com.example.myfarmshop;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class MyRecyclerViewAdapterSearch extends RecyclerView.Adapter<MyRecyclerViewAdapterSearch.ViewHolder> {

    private List<ModelFarms> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    MyRecyclerViewAdapterSearch(Context context, List<ModelFarms> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ModelFarms f = mData.get(position);
        holder.myTextName.setText("NAME: " + f.getName());
        holder.myTextAddress.setText("ADDRESS: " + f.getAddres());
        holder.myTextPhone.setText("PHONE: " + f.getPhone());
        holder.myTextWeb.setText("WEB: " + f.getWeb());
        holder.myTextCity.setText("CITY: " + f.getCity());
        holder.myTextProvince.setText("PROVINCE: " + f.getProvince());
        holder.myTextProduct.setText("PRODUCT: " + f.getProducts());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextName;
        TextView myTextAddress;
        TextView myTextPhone;
        TextView myTextWeb;
        TextView myTextCity;
        TextView myTextProvince;
        TextView myTextProduct;

        ViewHolder(View view) {
            super(view);
            myTextName = view.findViewById(R.id.name);
            myTextAddress = view.findViewById(R.id.address);
            myTextPhone = view.findViewById(R.id.phone);
            myTextWeb = view.findViewById(R.id.web);
            myTextCity = view.findViewById(R.id.city);
            myTextProvince = view.findViewById(R.id.province);
            myTextProduct = view.findViewById(R.id.product);
        }


    }

    // convenience method for getting data at click position
    ModelFarms getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
