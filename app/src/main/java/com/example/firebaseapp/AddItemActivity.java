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

public class AddItemActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addItemBtn;
    private TextInputEditText itemNameEdt, itemDescEdt, itemPriceEdt, bestSuitedEdt, itemImgEdt, itemLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String itemID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        // initializing all the variables.
        addItemBtn = findViewById(R.id.idBtnAddItem);
        itemNameEdt = findViewById(R.id.idEdtItemName);
        itemDescEdt = findViewById(R.id.idEdtItemDescription);
        itemPriceEdt = findViewById(R.id.idEdtItemPrice);
        bestSuitedEdt = findViewById(R.id.idEdtSuitedFor);
        itemImgEdt = findViewById(R.id.idEdtItemImageLink);
        itemLinkEdt = findViewById(R.id.idEdtItemLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating the database reference.
        databaseReference = firebaseDatabase.getReference("Items");
        // adding click listener for the add item button.
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String itemName = itemNameEdt.getText().toString();
                String itemDesc = itemDescEdt.getText().toString();
                String itemPrice = itemPriceEdt.getText().toString();
                String bestSuited = bestSuitedEdt.getText().toString();
                String itemImg = itemImgEdt.getText().toString();
                String itemLink = itemLinkEdt.getText().toString();
                itemID = itemName;
                // on below line we are passing all data to our modal class.
                ItemRVModal itemRVModal = new ItemRVModal(itemID, itemName, itemDesc, itemPrice, bestSuited, itemImg, itemLink);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(itemID).setValue(itemRVModal);
                        // displaying a toast message.
                        Toast.makeText(AddItemActivity.this, "Item Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AddItemActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AddItemActivity.this, "Fail to add Item..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}