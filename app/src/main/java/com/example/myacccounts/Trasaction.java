package com.example.myacccounts;

import android.os.Parcel;
import android.os.Parcelable;

public class Trasaction implements Parcelable {
    String credit;
    String debit;
    String discription;



    public Trasaction(String credit, String debit , String discription){

        this.credit=credit;
        this.debit=debit;
        this.discription=discription;



    }

    protected Trasaction(Parcel in) {
        credit = in.readString();
        debit = in.readString();
        discription = in.readString();

    }

    public static final Creator<Trasaction> CREATOR = new Creator<Trasaction>() {
        @Override
        public Trasaction createFromParcel(Parcel in) {
            return new Trasaction(in);
        }

        @Override
        public Trasaction[] newArray(int size) {
            return new Trasaction[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(credit);
        dest.writeString(debit);
        dest.writeString(discription);

    }
}
