package com.example.myacccounts;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myaccounts.R;

public class CreateUser extends AppCompatActivity{


    private static final int RESULT_PICK_CONTACT = 1;


    private   EditText namee;
    private   EditText phoneNmbr;
    private   EditText city;
    private   EditText address;
    private   EditText openingBalance;
    private   EditText days;


    Toolbar toolbar2;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar2, menu);

        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        namee = findViewById(R.id.editText);
        phoneNmbr = findViewById(R.id.editText2);
        city = findViewById(R.id.editText3);
        address = findViewById(R.id.editText4);
        openingBalance = findViewById(R.id.editText5);
        days = findViewById(R.id.editText6);
        toolbar2 = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar2);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contacts_id:
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(intent, RESULT_PICK_CONTACT);

                return true;
            case R.id.done_id:


            String name=namee.getText().toString();
            String phonenumber=phoneNmbr.getText().toString();
            String place=city.getText().toString();
            String address= this.address.getText().toString();
                String opBalance;
            if(openingBalance.getText().toString().isEmpty()){
                opBalance="0";
            }else {
                opBalance=openingBalance.getText().toString();
            }

            String noDays=days.getText().toString();

            if (name.isEmpty()){

                AlertDialog.Builder builder=new AlertDialog.Builder(CreateUser.this);
                builder.setCancelable(true);
                builder.setTitle("Error!!!");
                builder.setMessage("Field Name Cannot be empty");
                builder.setPositiveButton("Ok! Got It", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
            else {
                DbHelper dbHelper=new DbHelper(getApplicationContext());
                dbHelper.insertUserDetails(name,phonenumber,place,address,opBalance,noDays);
                Log.d("vw","create user (iud)");

                dbHelper.insertTransaction(null,null,opBalance,null);


                Toast.makeText(this, "User saved successfully", Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getApplicationContext(), com.example.myacccounts.MainActivity.class);
                startActivity(intent2);

            }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode != RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    contactPicked(data);
                    break;
            }
        } else {

            Toast.makeText(this, "failed to pick contact", Toast.LENGTH_SHORT).show();
        }
    }

    private void contactPicked(Intent data) {
        Cursor cursor = null;
        try {
            String phoneNo = null;
            String name = null;
            Uri uri = data.getData();
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();
            int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            int nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            name = cursor.getString(nameIndex);
            phoneNo = cursor.getString(phoneIndex);
            Log.d("vw",name);
            namee.setText(name);
            phoneNmbr.setText(phoneNo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}