package com.example.firebaseapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemRVAdapter extends RecyclerView.Adapter<ItemRVAdapter.ViewHolder> {
    // creating variables for our list, context, interface and position.
    private ArrayList<ItemRVModal> itemRVModalArrayList;
    private Context context;
    private ItemClickInterface itemClickInterface;
    int lastPos = -1;

    // creating a constructor.
    public ItemRVAdapter(ArrayList<ItemRVModal> itemRVModalArrayList, Context context, ItemClickInterface itemClickInterface) {
        this.itemRVModalArrayList = itemRVModalArrayList;
        this.context = context;
        this.itemClickInterface = itemClickInterface;
    }

    @NonNull
    @Override
    public ItemRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflating our layout file on below line.
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // setting data to our recycler view item on below line.
        ItemRVModal itemRVModal = itemRVModalArrayList.get(position);
        holder.itemTV.setText(itemRVModal.getItemName());
        holder.itemPriceTV.setText("Price $ " + itemRVModal.getItemPrice());
        Picasso.get().load(itemRVModal.getItemImg()).into(holder.itemIV);
        // adding animation to recycler view item on below line.
        setAnimation(holder.itemView, position);
        holder.itemIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickInterface.onItemClick(position);
            }
        });
    }

    private void setAnimation(View itemView, int position) {
        if (position > lastPos) {
            // on below line we are setting animation.
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            itemView.setAnimation(animation);
            lastPos = position;
        }
    }

    @Override
    public int getItemCount() {
        return itemRVModalArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // creating variable for our image view and text view on below line.
        private ImageView itemIV;
        private TextView itemTV, itemPriceTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing all our variables on below line.
            itemIV = itemView.findViewById(R.id.idIVItem);
            itemTV = itemView.findViewById(R.id.idTVItemName);
            itemPriceTV = itemView.findViewById(R.id.idTVItemPrice);
        }
    }

    // creating a interface for on click
    public interface ItemClickInterface {
        void onItemClick(int position);
    }
}