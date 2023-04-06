package com.example.firebaseapp;


import android.os.Parcel;
import android.os.Parcelable;

public class ItemRVModal implements Parcelable {
    // creating variables for our different fields.
    private String itemName;
    private String itemDescription;
    private String itemPrice;
    private String bestSuitedFor;
    private String itemImg;
    private String itemLink;
    private String itemId;


    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }


    // creating an empty constructor.
    public ItemRVModal() {

    }

    protected ItemRVModal(Parcel in) {
        itemName = in.readString();
        itemId = in.readString();
        itemDescription = in.readString();
        itemPrice = in.readString();
        bestSuitedFor = in.readString();
        itemImg = in.readString();
        itemLink = in.readString();
    }

    public static final Creator<ItemRVModal> CREATOR = new Creator<ItemRVModal>() {
        @Override
        public ItemRVModal createFromParcel(Parcel in) {
            return new ItemRVModal(in);
        }

        @Override
        public ItemRVModal[] newArray(int size) {
            return new ItemRVModal[size];
        }
    };

    // creating getter and setter methods.
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getBestSuitedFor() {
        return bestSuitedFor;
    }

    public void setBestSuitedFor(String bestSuitedFor) {
        this.bestSuitedFor = bestSuitedFor;
    }

    public String getItemImg() {
        return itemImg;
    }

    public void setItemImg(String itemImg) {
        this.itemImg = itemImg;
    }

    public String getItemLink() {
        return itemLink;
    }

    public void setItemLink(String itemLink) {
        this.itemLink = itemLink;
    }


    public ItemRVModal(String itemId, String itemName, String itemDescription, String itemPrice, String bestSuitedFor, String itemImg, String itemLink) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.bestSuitedFor = bestSuitedFor;
        this.itemImg = itemImg;
        this.itemLink = itemLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemName);
        dest.writeString(itemId);
        dest.writeString(itemDescription);
        dest.writeString(itemPrice);
        dest.writeString(bestSuitedFor);
        dest.writeString(itemImg);
        dest.writeString(itemLink);
    }
}