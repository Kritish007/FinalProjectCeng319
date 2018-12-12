package com.example.n01202172.myapplication;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Statistics extends AppCompatActivity {

    private LineChart linechart;
    private int N = 10;
    private FirebaseDatabase database = null;
    private DatabaseReference ref = null;
    private DatabaseReference myRef = null;
    private FirebaseAuth mAuth;
    String[] XLabels = new String[N];
    List<Database> firebaseData;

    public Statistics() {
        firebaseData = new ArrayList<Database>();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        linechart = findViewById(R.id.line_chart);

        // TODO 2: Find the Database.
        database = FirebaseDatabase.getInstance();
       // ref = database.getReference("parent");

        //test
        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference(mAuth.getUid()+"/graph");

       // mAuth = FirebaseAuth.getInstance();
       // myRef = FirebaseDatabase.getInstance().getReference(mAuth.getUid()+"/graph");
       // ref = FirebaseDatabase.getInstance().getReference(mAuth.getUid()+"/graph");

        // TODO 3: Load the data from database.
        loadDatabase(myRef);


    }
    private void drawGraph(List<Database> dataGenerated) {
        // TODO: Set text description of the xAxis
        Description desc = new Description();
        desc.setText("BATTERY USAGE");
        desc.setTextSize(12);
        desc.setPosition(1000,45);
        linechart.setDescription(desc);
        // TODO: Set the X-axis labels
        setAndValidateLabels();

        // TODO: Set LineData Entries


        int i = 0;
//        ArrayList entrylist = new ArrayList();
        List<Entry> entrylist = new ArrayList();
        // TODO: Entry is the element of the data input to the chart. All the data should be organized as Entries' ArrayList
        for (Database ds: dataGenerated){
            Entry e = new Entry (i++, Float.parseFloat(ds.getTemp()));
            entrylist.add(e);
        }

        // TODO: find the dataset of the ArrayList.
        LineDataSet dataset = new LineDataSet(entrylist, "Battery");
        dataset.setColor(R.color.colorAccent);  // set the color of this chart.
        // TODO: Get the LineData Object from dataset.
        LineData linedata = new LineData(dataset);
        linechart.setData(linedata);
        // TODO: This is a must to refresh the chart.
        linechart.invalidate(); // refresh
    }

    private void setAndValidateLabels() {
        // TODO: Set the labels to be displayed.
        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(-30f);  // rotate the xAxis label for 30 degrees.
        xAxis.setValueFormatter(new IAxisValueFormatter(){
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return XLabels[(int) value];
            }
        });
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 3
    }

    private void loadDatabase(DatabaseReference ref) {
        // Last N data entries from Database, these are automatically the N most recent data



        Query recentPostsQuery = ref.limitToFirst(N);


        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("MapleLeaf", "finished");
                // TODO 4: Now all the query data is in List firebaseData, Follow the similar procedure in Line activity.
                drawGraph(firebaseData);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        recentPostsQuery.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot != null && dataSnapshot.getValue() != null) {
                    // TODO: handle all the returned data. Similar to the Firebase read structure event.
                    Database dataStructure = new Database();

                    dataStructure.setTemp(Objects.requireNonNull(dataSnapshot.getValue(Database.class)).getBat());

                    String timestamp = Objects.requireNonNull(dataSnapshot.getValue(Database.class)).getTimestamp();
                    dataStructure.setTimestamp(timestamp);
                    // TODO: Define the XLabels of the chart.
                    XLabels[firebaseData.size()] = convertTimestamp(timestamp);

                    firebaseData.add(dataStructure);  // now all the data is in arraylist.
                    Log.d("MapleLeaf", "dataStructure " + dataStructure.getTimestamp());
                }
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String convertTimestamp(String timestamp){

        // Convert timestamp to text.
        long yourSeconds = Long.valueOf(timestamp);
        Date mDate = new Date(yourSeconds*1000);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy");
        // df.setTimeZone(TimeZone.getTimeZone("Etc/GMT-5"));
        DateFormat df1 = new SimpleDateFormat("hh:mm:ss");
        Log.d("MapleLeaf", df.format(mDate) +System.lineSeparator() + df1.format(mDate));
        return df.format(mDate) +System.lineSeparator() + df1.format(mDate);
    }

    }

