package com.example.myacccounts;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myaccounts.R;

import java.util.ArrayList;

public class TrasactionActivity extends AppCompatActivity {

    String credit;

    Toolbar toolbar3;
    ImageButton creditButton;
    ImageButton debitButton;
    TextView tv;
    TextView tv2;
    TextView tv3;
    TextView tv4;
    TextView tv5;
    TextView tv6;
    TextView debitAmt;
    TextView creditAmt;
    TextView totalText;
    TextView closingBalanceText;
    TextView closingAmt;
    TextView indicator;
    Button btn;
    LinearLayout ln2;
    public static final ArrayList<Trasaction> trasactionList = new ArrayList<Trasaction>();
    public static String id;
    public static double closingBalance = 0;
    private static String openingBalance ;
    private RecyclerView recyclerView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onclick);
        creditButton = findViewById(R.id.imageButton);
        debitButton = findViewById(R.id.imageButton2);

        tv = findViewById(R.id.textView);
        tv2 = findViewById(R.id.textView2);
        tv3 = findViewById(R.id.textView3);
        tv4 = findViewById(R.id.textView4);
        tv5 = findViewById(R.id.textView5);
        tv6 = findViewById(R.id.textView12);
        btn = findViewById(R.id.button);
        ln2 = findViewById(R.id.linearLayout2);
        debitAmt = findViewById(R.id.debit_id);
        creditAmt = findViewById(R.id.credit_id);
        closingAmt = findViewById(R.id.closingBalance_id);



        creditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent credit = new Intent(getApplicationContext(), Creditpage.class);
                credit.putExtra("id", id);
                startActivity(credit);


            }
        });

        debitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent debit = new Intent(getApplicationContext(), Debitpage.class);
                debit.putExtra("id", id);
                startActivity(debit);


            }
        });

        btn.setOnClickListener(new View.OnClickListener() {

            boolean visible;

            @Override
            public void onClick(View v) {
//                ln2.setVisibility(View.VISIBLE);
                visible = !visible;
                ln2.setVisibility(visible ? View.VISIBLE : View.GONE);


            }
        });


        toolbar3 = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar3);
        setTitle("Ledger");


        recyclerView2 = findViewById(R.id.rv2);

    }


    @Override
    protected void onResume() {
        super.onResume();

        String name;
        String nmbr;
        String place;
        String address;

        String days;


        trasactionList.clear();
        double creditSum = 0;
        double opBlnce = 0;
        double debitSum = 0;
        if (getIntent().hasExtra("name")) {
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            address = getIntent().getStringExtra("address");
            days = getIntent().getStringExtra("days");
            place = getIntent().getStringExtra("place");
            nmbr = getIntent().getStringExtra("nmbr");
            openingBalance = getIntent().getStringExtra("openingBalance");

            trasactionList.add(new Trasaction(null,openingBalance,"opening"));

            tv.setText(name);
            tv2.setText(nmbr);
            tv3.setText(address);
            tv4.setText(place);
//            tv6.setText((openingBalance+""))
        }

        opBlnce=Double.parseDouble(openingBalance);
        debitSum=opBlnce;
        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.transactionDetails(id);

        while (cursor.moveToNext()) {
            String credit = cursor.getString(cursor.getColumnIndex(dbHelper.col_credit));
            String debit = cursor.getString(cursor.getColumnIndex(dbHelper.col_debit));
            String discription = cursor.getString(cursor.getColumnIndex(dbHelper.col_discription));
//            debit=debit+openingBalance;
            trasactionList.add(new Trasaction(credit, debit, discription));
            if (credit!=null && !credit.isEmpty()) {
                creditSum = creditSum + Double.parseDouble(credit);
            }

            if (debit!=null && !debit.isEmpty()){/*second condition will not be excecuted if the first one is false*/

                debitSum= debitSum+ Double.parseDouble(debit) ;
            }
        }

        closingBalance =(creditSum-debitSum);

      /*  if (creditSum==0 && debitSum==0){
            closingAmt.setText(String.valueOf(Math.abs(opBlnce))  );
        }
        else {
            closingAmt.setText(String.valueOf(Math.abs(closingBalance))  );
        }
*/

        indicator=findViewById(R.id.indication_id);
        if (closingBalance>0){
            indicator.setText("Cr");}
        else{
            indicator.setText("Dr");
        }
        closingAmt.setText(String.valueOf(Math.abs(closingBalance))  );
        creditAmt.setText(String.valueOf(creditSum));
        debitAmt.setText(String.valueOf(debitSum));


        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(new UserAdapter2(trasactionList));

    }

}




