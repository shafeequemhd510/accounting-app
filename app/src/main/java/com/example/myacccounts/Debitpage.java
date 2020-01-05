package com.example.myacccounts;

import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.myaccounts.R;

public class Debitpage extends AppCompatActivity {

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar4, menu);

        return true;
    }
    public static String id;
    EditText et8;
    EditText discriptionDebit;
    Toolbar toolbar5;
    DbHelper mydb;
    Layout ln2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debitpage);

        discriptionDebit=findViewById(R.id.discriptionDebit_id);
        et8 = findViewById(R.id.editText8);
        toolbar5 = findViewById(R.id.toolbar5);
        setSupportActionBar(toolbar5);
        if (getIntent().hasExtra("id")) {
            id = getIntent().getStringExtra("id");

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.done_iddebit:
                String textvar8 = et8.getText().toString();
                String textvar9 = discriptionDebit.getText().toString();
                DbHelper dbHelper2 = new DbHelper(getApplicationContext());
                dbHelper2.insertTransaction(id,null,textvar8,textvar9);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}


