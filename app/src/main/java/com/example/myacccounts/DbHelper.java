package com.example.myacccounts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


class DbHelper extends SQLiteOpenHelper {

    String userDetails = "userdetails";
    String transaction = "transactn";
    String col_ID="id";
    String col_Name = "Name";
    String col_phoneNmbr = "phoneNmbr";
    String col_city = "city";
    String col_address = "address";
    String col_openingBalance = "openingBalance";
    String col_days = "days";
    String col_debit= "debit";
    String col_credit= "credit";
    String col_closingBalance= "closingBalance";
    String col_userID= "userID";
    String col_discription= "discription";



    public DbHelper(@Nullable Context context) {
        super(context, "mydb", null, 4);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


          /*db.execSQL("create table "+userDetails+"( "+col_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+ col_Name+" TEXT , "+ col_phoneNmbr+" TEXT , "+col_city+" TEXT ,"+col_address+" TEXT" +
                  " ,"+ col_openingBalance+" INTEGER ,"+col_days+" INTEGER )");*/

          db.execSQL("create table "+userDetails+"( "+col_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ," +
                  " "+ col_Name+" TEXT , "+ col_phoneNmbr+" TEXT , "+col_city+" TEXT ,"+col_address+" TEXT" +
                  " ,"+ col_openingBalance+" INTEGER ,"+col_days+" INTEGER,"+col_closingBalance+" INTEGER  )");

        String sqlTable2 = ("create table " +transaction+"( "+col_ID+" INTEGER PRIMARY KEY AUTOINCREMENT ,"+col_userID
                +" INTEGER,"+col_debit+" INTEGER,"+col_credit+ " INTEGER , "+
                col_discription+" TEXT)");
                Log.d("vw","query "+sqlTable2);
        db.execSQL(sqlTable2);





    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +userDetails);
        db.execSQL("DROP TABLE IF EXISTS " +transaction);
        onCreate(db);
    }

public void insertUserDetails (String name,String pnmbr,String city,String address,String openingbalance,String days){
        Log.d("vw ","dbhelper(iud) ");

    ContentValues cv=new ContentValues();
    cv.put(col_Name,name);
    cv.put(col_phoneNmbr, pnmbr);
    cv.put(col_city, city);
    cv.put(col_address, address);
    cv.put(col_openingBalance, openingbalance);
    cv.put(col_days, days);
    SQLiteDatabase db=this.getWritableDatabase();
    db.insert(userDetails,null,cv);

}
public Cursor getUserDetails(){
    Log.d("vw","get user in dbhelper (gud)");
        SQLiteDatabase db=getReadableDatabase();
        String query="Select * from "+userDetails;
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

}
public Cursor transactionDetails(String userId){
    Log.d("vw","get user in dbhelper (gud)");
        SQLiteDatabase db=getReadableDatabase();
        String query="Select * from "+transaction+" where "+col_userID+"="+userId ;
        Cursor cursor=db.rawQuery(query,null);
        return cursor;

}

    public void insertTransaction(String userId, String credit  , String debit , String discription ) {

        Log.d("vw", "insertTransaction in dbhelper : ");
        ContentValues cv=new ContentValues();
        cv.put(col_credit,credit);
        cv.put(col_debit,debit);
        cv.put(col_userID,userId);
        cv.put(col_discription,discription);

        SQLiteDatabase db=this.getWritableDatabase();
        db.insert(transaction,null,cv);
    }
  /*  public boolean updateData(String id ,String credit){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(col_ID,id);
        cv.put(col_credit,credit);
//        cv.put(col_credit,col_debit);
        db.update(userDetails,cv,"id = ?",new String[] {id});
        return true;
*/public void deleteRow(String userId)
  {
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL("DELETE FROM " +userDetails + " WHERE "+col_ID+"='"+userId+"'");
      db.execSQL("DELETE FROM " +transaction + " WHERE "+col_userID+"='"+userId+"'");
      db.close();
  }


    }


