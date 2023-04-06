package com.example.firebaseapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditItemActivity extends AppCompatActivity {

    // creating variables for the edit text, firebase database,
    // database reference, item rv modal,progress bar.
    private TextInputEditText itemNameEdt, itemDescEdt, itemPriceEdt, bestSuitedEdt, itemImgEdt, itemLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ItemRVModal itemRVModal;
    private ProgressBar loadingPB;
    // creating a string for item id.
    private String itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        // initializing all our variables on below line.
        Button addItemBtn = findViewById(R.id.idBtnAddItem);
        itemNameEdt = findViewById(R.id.idEdtItemName);
        itemDescEdt = findViewById(R.id.idEdtItemDescription);
        itemPriceEdt = findViewById(R.id.idEdtItemPrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        itemImgEdt = findViewById(R.id.idEdtItemImageLink);
        itemLinkEdt = findViewById(R.id.idEdtItemLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        itemRVModal = getIntent().getParcelableExtra("item");
        Button deleteItemBtn = findViewById(R.id.idBtnDeleteItem);

        if (itemRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            itemNameEdt.setText(itemRVModal.getItemName());
            itemPriceEdt.setText(itemRVModal.getItemPrice());
            bestSuitedEdt.setText(itemRVModal.getBestSuitedFor());
            itemImgEdt.setText(itemRVModal.getItemImg());
            itemLinkEdt.setText(itemRVModal.getItemLink());
            itemDescEdt.setText(itemRVModal.getItemDescription());
            itemID = itemRVModal.getItemId();
        }

        // on below line we are initializing the database reference and adding a child as the item id.
        databaseReference = firebaseDatabase.getReference("Items").child(itemID);
        // on below line we are adding click listener for our add item button.
        addItemBtn.setOnClickListener(v -> {
            // on below line we are making progress bar as visible.
            loadingPB.setVisibility(View.VISIBLE);
            // on below line we are getting data from edit text.
            String itemName = Objects.requireNonNull(itemNameEdt.getText()).toString();
            String itemDesc = Objects.requireNonNull(itemDescEdt.getText()).toString();
            String itemPrice = Objects.requireNonNull(itemPriceEdt.getText()).toString();
            String bestSuited = Objects.requireNonNull(bestSuitedEdt.getText()).toString();
            String itemImg = Objects.requireNonNull(itemImgEdt.getText()).toString();
            String itemLink = Objects.requireNonNull(itemLinkEdt.getText()).toString();
            // on below line we are creating a map for
            // passing a data using key and value pair.
            Map<String, Object> map = new HashMap<>();
            map.put("itemName", itemName);
            map.put("itemDescription", itemDesc);
            map.put("itemPrice", itemPrice);
            map.put("bestSuitedFor", bestSuited);
            map.put("itemImg", itemImg);
            map.put("itemLink", itemLink);
            map.put("itemId", itemID);

            // on below line we are calling a database reference on
            // add value event listener and on data change method
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    // making progress bar visibility as gone.
                    loadingPB.setVisibility(View.GONE);
                    // adding a map to our database.
                    databaseReference.updateChildren(map);
                    // on below line we are displaying a toast message.
                    Toast.makeText(EditItemActivity.this, "Item Updated..", Toast.LENGTH_SHORT).show();
                    // opening a new activity after updating item.
                    startActivity(new Intent(EditItemActivity.this, MainActivity.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // displaying a failure message on toast.
                    Toast.makeText(EditItemActivity.this, "Fail to update item..", Toast.LENGTH_SHORT).show();
                }
            });
        });

        // adding a click listener for our delete item button.
        deleteItemBtn.setOnClickListener(v -> {
            // calling a method to delete item.
            deleteItem();
        });

    }

    private void deleteItem() {
        // on below line calling a method to delete the item.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Item Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditItemActivity.this, MainActivity.class));
    }
}