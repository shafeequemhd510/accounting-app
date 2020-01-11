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

public class DebitPageActivity extends AppCompatActivity {

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
    String textvar8;


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

                if (et8.getText().toString().isEmpty()){

                        AlertDialog.Builder builder=new AlertDialog.Builder(DebitPageActivity.this);
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
                    textvar8 = et8.getText().toString();
                    String textvar9 = discriptionDebit.getText().toString();
                    DbHelper dbHelper2 = new DbHelper(getApplicationContext());
                    dbHelper2.insertTransaction(id, null, textvar8, textvar9);
                    finish();
                    return true;
                }
                    default:
                        return super.onOptionsItemSelected(item);

        }

    }
}


