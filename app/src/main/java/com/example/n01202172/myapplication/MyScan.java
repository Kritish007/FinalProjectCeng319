package com.example.n01202172.myapplication;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyScan extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    Database mData;
    private TextView result;
    private TextView fruitname;
    private TextView time;


    private static final String[] paths = {"Apple", "Banana", "Strawberry"};

    String strawberry = "fc5a8d";
    String strawHex = strawberry;
    int strawdecimal = Integer.parseInt(strawHex, 16); //16538253

    String apple = "ff0800";
    String appleHex = apple;
    int appledecimal = Integer.parseInt(appleHex, 16); //16713728

    String banana = "ffe135";
    String bananaHex = banana;
    int bananadecimal = Integer.parseInt(bananaHex, 16); //16769333

    String orange = "ffa500";
    String orangeHex = orange;
    int orangedecimal = Integer.parseInt(orangeHex, 16); //16753920

    String blueberry = "4f86f7";
    String blueberryHex = blueberry;
    int blueberrydecimal = Integer.parseInt(blueberryHex, 16); //5211895

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myscan);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_keyboard_arrow_left_black_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Button btn1 = (Button) findViewById(R.id.button);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result = findViewById(R.id.textView6);
                fruitname = findViewById(R.id.textView17);
                time = findViewById(R.id.textView13);
                dataProcess();
            }
        });
    }

    public void dataProcess(){
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(mAuth.getUid()+"/sensordata/data1");
       // myRef = FirebaseDatabase.getInstance().getReference("sensordata/data");
        ValueEventListener valueEventListener = myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Database ds = (Database) dataSnapshot.getValue(Database.class);
                String rbg = ds.getRgb();
                String hexNumber = rbg;
                int decimal = Integer.parseInt(hexNumber, 16);
                if (decimal >= (appledecimal-1000) && decimal <= (appledecimal+1000)) {
                    result.setText("Fruit: Apple ");

                    if (decimal == appledecimal) {
                        result.setText("Fruit Condition: Apple is ready");
                    }
                    if (decimal > appledecimal) {
                        result.setText("Fruit Condition: Apple is over ripen");
                    }
                    if (decimal < appledecimal) {
                        result.setText("Fruit Condition: Apple is not ready for pickup");
                    }
                }
                else if (decimal >= (strawdecimal-1000) && decimal <= (strawdecimal+1000)) {
                    result.setText("Fruit: Strawberry");

                    if (decimal == strawdecimal) {
                        result.setText("Fruit Condition: Strawberry is ready");
                    }
                    if (decimal > strawdecimal) {
                        result.setText("Fruit Condition: Strawberry is over ripen");
                    }
                    if (decimal < strawdecimal) {
                        result.setText("Fruit Condition: Strawberry is not ready for pickup");
                    }
                }
                else if (decimal >= (bananadecimal-1000) && decimal <= (bananadecimal+1000)) {
                    result.setText("Fruit: Banana ");

                    if (decimal == bananadecimal) {
                        result.setText("Fruit Condition: Banana is ready");
                    }
                    if (decimal > bananadecimal) {
                        result.setText("Fruit Condition: Banana is over ripen");
                    }
                    if (decimal < bananadecimal) {
                        result.setText("Fruit Condition: Banana is not ready for pickup");
                    }
                }
                else if (decimal >= (orangedecimal-1000) && decimal <= (orangedecimal+1000)) {
                    result.setText("Fruit: Orange ");

                    if (decimal == orangedecimal) {
                        result.setText("Fruit Condition: Orange is ready");
                    }
                    if (decimal > orangedecimal) {
                        result.setText("Fruit Condition: Orange is over ripen");
                    }
                    if (decimal < orangedecimal) {
                        result.setText("Fruit Condition: Orange is not ready for pickup");
                    }
                }
                else if (decimal >= (blueberrydecimal-1000) && decimal <= (blueberrydecimal+1000)) {
                    result.setText("Fruit: Blueberry ");

                    if (decimal == blueberrydecimal) {
                        result.setText("Fruit Condition: Blueberry is ready");
                    }
                    if (decimal > blueberrydecimal) {
                        result.setText("Fruit Condition: Blueberry is over ripen");
                    }
                    if (decimal < blueberrydecimal) {
                        result.setText("Fruit Condition: Blueberry is not ready for pickup");
                    }
                }

                time.setText("Scan time: " +convertTimestamp(ds.getTimestamp()));
                fruitname.setText("Fruit Name:" + ds.getFruitname());


            }


            @RequiresApi(api = Build.VERSION_CODES.N)
            private String convertTimestamp(String timestamp) {

                long yourSeconds = Long.valueOf(timestamp);
                Date mDate = new Date(yourSeconds * 1000);
                DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss");
                return df.format(mDate);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MyScan.this, "Data unavailable, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }}

