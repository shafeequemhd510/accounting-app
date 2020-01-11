package com.example.myacccounts;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myaccounts.R;

public class CreditPageActivity extends AppCompatActivity {

    public static String id;

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar3, menu);

        return true;
    }

    EditText et7;
    EditText discriptionCredit;
    Toolbar toolbar4;
    DbHelper mydb;
    Layout ln2;

    String textvar7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditpage);

        discriptionCredit=findViewById(R.id.discriptionCredit_id);
        et7 = findViewById(R.id.editText7);
        toolbar4 = findViewById(R.id.toolbar4);
        setSupportActionBar(toolbar4);


        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_idcredit:
                if (et7.getText().toString().isEmpty()){

                    AlertDialog.Builder builder=new AlertDialog.Builder(CreditPageActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Error!!!");
                    builder.setMessage("Field Amount Cannot be empty");
                    builder.setPositiveButton("Ok! Got It", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                }
                else {
                    textvar7 = et7.getText().toString();
                    String textvar10 = discriptionCredit.getText().toString();
                    DbHelper dbHelper2 = new DbHelper(getApplicationContext());
                    dbHelper2.insertTransaction(id, textvar7, null, textvar10);
//                dbHelper2.insertTransaction(userId);


                    finish();


               /* Intent intent3 = new Intent(getApplicationContext(), TrasactionActivity.class);
                startActivity(intent3);*/
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
