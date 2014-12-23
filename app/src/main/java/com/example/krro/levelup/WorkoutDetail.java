package com.example.krro.levelup;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class WorkoutDetail extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    ListView lvWorkout;
    ArrayList<Integer> arrID;
    String[] arrBeschreibung;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutdetail);

        Intent uebungDetail = getIntent();
        String beschreibung = uebungDetail.getStringExtra("beschreibung");
        TextView tvWorkout = (TextView)findViewById(R.id.tvWorkout);
        tvWorkout.setText(beschreibung);

        setListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        setListView();
    }

    @Override
    protected void onPause() {
        super.onPause();

        cursor.close();
        db.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cursor.close();
        db.close();
    }

    public void setListView()
    {
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        lvWorkout = (ListView)findViewById(R.id.selectUebungen);

        String query = "SELECT u_id as _id, beschreibung, bild, bauch, bizeps, trizeps, brust, schulter, ruecken, beine FROM uebungen;";
        cursor = db.rawQuery(query, null);

        arrID = new ArrayList<Integer>();
        MyCursorAdapter adapter;

        if(cursor.getCount() != 0) {
            adapter = new MyCursorAdapter(this, cursor);
            lvWorkout.setAdapter(adapter);
        }
    }
}
