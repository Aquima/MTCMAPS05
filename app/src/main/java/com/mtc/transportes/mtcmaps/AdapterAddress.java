package com.mtc.transportes.mtcmaps;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import API.Model.AddressComponent;

/**
 * Created by raulquispe on 3/15/18.
 */

public class AdapterAddress extends RecyclerView.Adapter<AdapterAddress.ViewHolderAddress>{
    List<AddressComponent> data;
    Context context;
    public AdapterAddress(List<AddressComponent> listAddress,Context context){
        this.data = listAddress;
        this.context = context;
    }
    @Override
    public ViewHolderAddress onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_cell,null,false);
        return new ViewHolderAddress(view);
    }

    @Override
    public void onBindViewHolder(ViewHolderAddress holder, int position) {
        holder.addAddressData(data.get(position),context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolderAddress extends RecyclerView.ViewHolder{
        TextView textViewAddress;
        ImageView imgView;
        public ViewHolderAddress(View itemView) {
            super(itemView);
            textViewAddress = itemView.findViewById(R.id.textAddress);
            imgView = itemView.findViewById(R.id.imgViewMTC);
        }

        public void addAddressData(AddressComponent addressComponent,Context context) {
            textViewAddress.setText(addressComponent.getLongName().toString());
            String urlMTC = "http://www.mtc.gob.pe/images/logo.png";

            Picasso.with(context).load(urlMTC).into(imgView);
        }
    }
}
