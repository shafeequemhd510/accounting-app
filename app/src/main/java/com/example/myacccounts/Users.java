package com.example.myacccounts;

import android.os.Parcel;
import android.os.Parcelable;

public class Users implements Parcelable {

    String id;
    String name;
    String place;
    String nmbr;
    String address;
    String openingBalance;
    String days;
    double closing;

    public Users(String id,String name, String nmbr, String place, String address, String openingBalance, String days, double closing){

        this.id=id;
        this.name=name;
        this.place=place;
        this.nmbr=nmbr;
        this.place=place;
        this.address=address;
        this.openingBalance=openingBalance;
        this.days=days;
        this.closing=closing;


    }

    protected Users(Parcel in) {
        id = in.readString();
        name = in.readString();
        place = in.readString();
        nmbr=in.readString();
        address=in.readString();
        openingBalance=in.readString();
        days=in.readString();
    }

    public static final Creator<Users> CREATOR = new Creator<Users>() {
        @Override
        public Users createFromParcel(Parcel in) {
            return new Users(in);
        }

        @Override
        public Users[] newArray(int size) {
            return new Users[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(place);
        dest.writeString(nmbr);
        dest.writeString(address);
        dest.writeString(openingBalance);
        dest.writeString(days);
    }
}
