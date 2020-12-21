package com.example.myfarmshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayInputStream;
import java.util.List;

public class FarmListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Farm> mList;
    private OnFarmListener mOnFarmListener;

    public FarmListAdapter(List<Farm> list, Context context, OnFarmListener onFarmListener){
        super();
        mList = list;
        this.mOnFarmListener = onFarmListener;
    }
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTextId;
        public TextView mTextName;
        public TextView mTextAddress;
        public TextView mTextCity;
        public TextView mTextProvince;
        public TextView mTextPhone;
        public TextView mTextUrl;
        public ImageView mImage;

        OnFarmListener onFarmListener;

        public ViewHolder(@NonNull View itemView, OnFarmListener onFarmListener) {
            super(itemView);
            //mTextId = (TextView) itemView.findViewById(R.id.textId);
            mTextName = (TextView) itemView.findViewById(R.id.textName);
            mTextAddress = (TextView) itemView.findViewById(R.id.textAddress);
            //mTextCity = (TextView) itemView.findViewById(R.id.textCity);
            //mTextProvince = (TextView) itemView.findViewById(R.id.textProvince);
            mTextPhone = (TextView) itemView.findViewById(R.id.textPhone);
            mTextUrl = (TextView) itemView.findViewById(R.id.textUrl);
            mImage = (ImageView) itemView.findViewById(R.id.imageView);

            this.onFarmListener = onFarmListener;

            itemView.setOnClickListener(this); // Pass this to the OnClickListener interface
        }

        @Override
        public void onClick(View v) {
            onFarmListener.onFarmClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mOnFarmListener);// Create the viewholder and pass the inflated view

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Farm farmAdapter = mList.get(position);
        //((ViewHolder) holder).mTextId.setText(Integer.toString(farmAdapter.getId()));
        ((ViewHolder) holder).mTextName.setText(farmAdapter.getName());
        ((ViewHolder) holder).mTextAddress.setText(farmAdapter.getAddress());
        //((ViewHolder) holder).mTextCity.setText(farmAdapter.getCity());
        //((ViewHolder) holder).mTextProvince.setText(farmAdapter.getPhone());
        ((ViewHolder) holder).mTextPhone.setText(farmAdapter.getPhone());
        ((ViewHolder) holder).mTextUrl.setText(farmAdapter.getUrl());
        if(farmAdapter.getPicture() != null) {
            ByteArrayInputStream imageStream = new ByteArrayInputStream(farmAdapter.getPicture());
            Bitmap bmp = BitmapFactory.decodeStream(imageStream);
            ((ViewHolder) holder).mImage.setImageBitmap(bmp);
        }else{
            ((ViewHolder) holder).mImage.setImageResource(R.drawable.farmfood);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnFarmListener {
        void onFarmClick (int position);
    }
}
