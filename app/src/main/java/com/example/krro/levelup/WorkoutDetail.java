package com.example.krro.levelup;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class WorkoutDetail extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    ListView lvWorkout;
    ArrayList<Integer> arrID;
    String[] arrBeschreibung;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutdetail);

        setListView();
    }


    public void setListView()
    {
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        lvWorkout = (ListView)findViewById(R.id.selectUebungen);

        String query = "SELECT u_id, beschreibung FROM uebungen;";
        Cursor cursor = db.rawQuery(query, null);

        arrID = new ArrayList<Integer>();
        arrBeschreibung = new String[cursor.getCount()];
        int[] tvBeschreibung = new int[]
                {R.id.tvBeschreibung};
        SimpleCursorAdapter adapter;

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            int i = 0;
            while (!cursor.isAfterLast()) {
                arrID.add(cursor.getInt(0));
                arrBeschreibung[i] = cursor.getString(1);
                i++;
                cursor.moveToNext();
            }

            adapter = new SimpleCursorAdapter(
                    this,
                    R.layout.uebungenliste,
                    cursor,
                    arrBeschreibung,
                    tvBeschreibung
            );
            lvWorkout.setAdapter(adapter);
        }

        cursor.close();
        db.close();
    }
}
