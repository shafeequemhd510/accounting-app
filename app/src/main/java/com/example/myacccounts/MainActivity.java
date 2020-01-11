package com.example.myacccounts;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myaccounts.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements UserAdapter.OnNoteListener {
    private AppBarConfiguration mAppBarConfiguration;

    DrawerLayout drawer ;
    NavigationView navigationView ;
    Toolbar toolbar;
    public static final ArrayList<Users> users = new ArrayList<Users>();

    DbHelper dbHelper;
    RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.app_bar, menu);

        return true;
    }

    @Override
    protected void onResume() {

        dbHelper = new DbHelper(this);

        Cursor cursor = dbHelper.getUserDetails();
        Log.d("vw", "get user details (in main)");
        users.clear();
        while (cursor.moveToNext()) {
            String id = cursor.getString((cursor.getColumnIndex(dbHelper.col_ID)));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.col_Name));
            String nmbr = cursor.getString(cursor.getColumnIndex(dbHelper.col_phoneNmbr));
            String place = cursor.getString(cursor.getColumnIndex(dbHelper.col_city));
            String address = cursor.getString(cursor.getColumnIndex(dbHelper.col_address));
            String openingBalance = cursor.getString(cursor.getColumnIndex(dbHelper.col_openingBalance));
            String days = cursor.getString(cursor.getColumnIndex(dbHelper.col_days));
            double closing = closingAmt(openingBalance,id);

            Log.d("inhilee", "name= " + name + "place= " + place + nmbr);

            users.add(new Users(id, name, nmbr, place, address, openingBalance, days ,closing ));


            recyclerView.setAdapter(new UserAdapter(users, this));


        }







        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);
        final ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);



       /* mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();*/

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.add_user:
                Intent i = new Intent(this, CreateUserActivity.class);
                this.startActivity(i);
//                finish();
                return true;


            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void OnNoteClick(Users users) {
//        users.get(position);
        Intent intent = new Intent(this, TrasactionActivity.class);
        intent.putExtra("id", users.id);
        intent.putExtra("name", users.name);
        intent.putExtra("address", users.address);
        intent.putExtra("days", users.days);
        intent.putExtra("nmbr", users.nmbr);
        intent.putExtra("openingBalance", users.openingBalance);
        intent.putExtra("place", users.place);
        startActivity(intent);
    }

    public double closingAmt(String openingBalance, String id) {
        DbHelper dbHelper = new DbHelper(this);
        Cursor cursor = dbHelper.getTransactionDetails(id);
        ArrayList<Trasaction> trasactionList = new ArrayList<Trasaction>();
        double creditSum = 0;
        double debitSum = 0;
        double closingBalance = 0;
        double opBlnce = 0;

        opBlnce=Double.parseDouble(openingBalance);
        debitSum=opBlnce;

        while (cursor.moveToNext()) {
            String TransactionId = cursor.getString(cursor.getColumnIndex(dbHelper.col_ID));
            String credit = cursor.getString(cursor.getColumnIndex(dbHelper.col_credit));
            String debit = cursor.getString(cursor.getColumnIndex(dbHelper.col_debit));
            String discription = cursor.getString(cursor.getColumnIndex(dbHelper.col_discription));
//            debit=debit+openingBalance;
            trasactionList.add(new Trasaction(TransactionId,credit, debit, discription));
            if (credit != null && !credit.isEmpty()) {
                creditSum = creditSum + Double.parseDouble(credit);
            }

            if (debit != null && !debit.isEmpty()) {/*second condition will not be excecuted if the first one is false*/

                debitSum = debitSum + Double.parseDouble(debit);
            }
        }

        closingBalance =creditSum-debitSum;
        return closingBalance;

    }


}


