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
import java.text.SimpleDateFormat;
import java.util.List;

public class SpecialOfferListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SpecialOffer> specialOfferList;
    private OnSpecialOfferListener mOnSpecialOfferListener;

    private boolean showAsCardView = true;  // Just a boolean used during development time. Shows records as a cardview or as a simple list

    public SpecialOfferListAdapter(List<SpecialOffer> list, Context context, OnSpecialOfferListener onSpecialOfferListener) {
        super();
        specialOfferList = list;
        this.mOnSpecialOfferListener = onSpecialOfferListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Objects to handle the textviews to be displayed
        public TextView textFarmIdOrName;
        public TextView textType;
        public TextView textDescription;
        public TextView textDateSince;
        public TextView textDateUntil;
        public TextView textDateRange;
        public ImageView farmImage;

        OnSpecialOfferListener onSpecialOfferListener;

        public ViewHolder(@NonNull View itemView, OnSpecialOfferListener onSpecialOfferListener) {
            super(itemView);

            // Instantiate the GUI objects
            if (showAsCardView) {
                textFarmIdOrName = (TextView) itemView.findViewById(R.id.textFarmName);
                textDescription = (TextView) itemView.findViewById(R.id.textSpecialOfferDescription);
                textDateRange = (TextView) itemView.findViewById(R.id.textDateRange);
                farmImage = (ImageView) itemView.findViewById(R.id.imageView);
            } else {
                textFarmIdOrName = (TextView) itemView.findViewById(R.id.textFarmId);
                textType = (TextView) itemView.findViewById(R.id.textType);
                textDescription = (TextView) itemView.findViewById(R.id.textDescription);
                textDateSince = (TextView) itemView.findViewById(R.id.textDateSince);
                textDateUntil = (TextView) itemView.findViewById(R.id.textDateUntil);
            }

            this.onSpecialOfferListener = onSpecialOfferListener;

            itemView.setOnClickListener(this); // Pass this to the OnClickListener interface
        }

        @Override
        public void onClick(View v) {
            onSpecialOfferListener.onSpecialOfferClick(getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the record_layout view
        View view;
        if (showAsCardView) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_offer_cardview_record_layout, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.special_offer_record_layout, parent, false);
        }

        ViewHolder viewHolder = new ViewHolder(view, mOnSpecialOfferListener); // Create the viewholder and pass the inflated view

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SpecialOffer specialOffer = specialOfferList.get(position);  // Obtain the item in the specified position
        //Farm farmAdapter = programList.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy"); // Format to display the dates

        if (showAsCardView) {
            // Display information of the Special Offer
            ((ViewHolder)holder).textFarmIdOrName.setText(specialOffer.getFarmName());
            ((ViewHolder)holder).textDescription.setText(String.format("%s: %s", specialOffer.getTypeName(), specialOffer.getDescription()));
            ((ViewHolder)holder).textDateRange.setText(String.format("Date: %s ~ %s",
                                                       dateFormat.format(specialOffer.getDateSince()),
                                                       dateFormat.format(specialOffer.getDateUntil())));

            // Display the picture if it is not null - otherwise, show a default picture of the app
            if(specialOffer.getFarmPicture() != null) {
                ByteArrayInputStream imageStream = new ByteArrayInputStream(specialOffer.getFarmPicture());
                Bitmap bmp = BitmapFactory.decodeStream(imageStream);
                ((ViewHolder) holder).farmImage.setImageBitmap(bmp);
            }else{
                ((ViewHolder) holder).farmImage.setImageResource(R.drawable.farmshop);
            }
        } else {
            // Set the values for the textviews based on the program object retrieved
            ((ViewHolder)holder).textFarmIdOrName.setText(String.valueOf(specialOffer.getFarmId()));
            ((ViewHolder)holder).textType.setText(specialOffer.getTypeName());
            ((ViewHolder)holder).textDescription.setText(specialOffer.getDescription());
            ((ViewHolder)holder).textDateSince.setText(dateFormat.format(specialOffer.getDateSince()));
            ((ViewHolder)holder).textDateUntil.setText(dateFormat.format(specialOffer.getDateUntil()));
        }
    }

    @Override
    public int getItemCount() {
        return specialOfferList.size();
    }

    public interface OnSpecialOfferListener {
        void onSpecialOfferClick (int position);
    }
}