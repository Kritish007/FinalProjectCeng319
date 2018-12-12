package com.example.n01202172.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.net.Uri;


import com.google.firebase.auth.FirebaseAuth;

public class MainScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public boolean onOptionsItemSelected(MenuItem item) {

        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            mAuth = FirebaseAuth.getInstance();
            mAuth.signOut(); // To logout from Database
            finishAffinity();
            Toast.makeText(this,"Logging Out",Toast.LENGTH_LONG).show();
        }

        if (id == R.id.about_us) {
            Intent intent = new Intent(MainScreen.this,AboutUs.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainscreen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
    }
    public void MyScan(View view){
        Intent intent = new Intent(MainScreen.this,MyScan.class);
        startActivity(intent);
    }

    public void MyBattery(View view){
        Intent intent = new Intent(MainScreen.this,MyBattery.class);
        startActivity(intent);
    }

    public void MyTemperature(View view){
        Intent intent = new Intent(MainScreen.this,MyTemperature.class);
        startActivity(intent);
    }

    public void Phone(View view){
        String number = "4373450995";
        Uri call = Uri.parse("tel:" + number);
        Intent surf = new Intent(Intent.ACTION_DIAL, call);
        startActivity(surf);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }





}
