package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkoutDetail extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    MyCursorAdapter adapter;

    ListView lvUebungen;
    ArrayList<Integer> arrID;
    ArrayList<Boolean> checkedItems = new ArrayList<Boolean>();

    boolean neuesWorkout = false;
    int id;
    String beschreibung;

    TextView tvWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutdetail);

        Intent uebungDetail = getIntent();
        neuesWorkout = uebungDetail.getBooleanExtra("neu", false);
        id = uebungDetail.getIntExtra("id", 999999);
        beschreibung = uebungDetail.getStringExtra("beschreibung");
        tvWorkout = (TextView)findViewById(R.id.tvWorkout);
        tvWorkout.setText(beschreibung);
        tvWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WorkoutDetail.this);

                alert.setTitle(R.string.neueBez);

                final EditText input = new EditText(WorkoutDetail.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        beschreibung = input.getText().toString();
                        tvWorkout.setText(beschreibung);
                    }
                });

                alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });

        setListView();

        Button btnSpeichern = (Button)findViewById(R.id.btnSpeichern);
        btnSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int workoutIndex;
                ContentValues headVal = new ContentValues();
                headVal.put("beschreibung", beschreibung);
                if (neuesWorkout)
                {
                    db.insert("workouthead", null,headVal);
                    Cursor c = db.rawQuery("SELECT MAX(w_id) FROM workouthead", null);
                    c.moveToFirst();
                    workoutIndex = c.getInt(0);
                    Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    workoutIndex = id;
                    db.update("workouthead", headVal, "w_id = " + workoutIndex, null);
                }

                ContentValues posVal = new ContentValues();
                checkedItems = adapter.getChecked();
                for (int i=0; i < checkedItems.size(); i++)
                {
                    if (checkedItems.get(i))
                    {
                        if(neuesWorkout)
                        {
                            posVal.put("w_id", workoutIndex);
                            posVal.put("u_id", arrID.get(i));
                            posVal.put("gewicht", 0);
                            posVal.put("wiederholungen", 0);
                            posVal.put("saetze", 0);
                            db.insert("workoutpos", null, posVal);
                        }
                    }
                    else
                    {

                    }
                }
                WorkoutDetail.this.finish();

                Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
            }
        });
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
        lvUebungen = (ListView)findViewById(R.id.selectUebungen);

        Log.v("index", id +"");
        String query = "SELECT u_id as _id, beschreibung, bild, bauch, "
                + "bizeps, trizeps, brust, schulter, ruecken, beine, "
                + "CASE WHEN EXISTS (SELECT w.u_id FROM workoutpos w WHERE w.w_id = " + id + " AND u.u_id = w.u_id) THEN 1 ELSE 0 END "
                + "FROM uebungen u;";
        cursor = db.rawQuery(query, null);

        arrID = new ArrayList<Integer>();

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                arrID.add(cursor.getInt(0));
                cursor.moveToNext();
            }

            adapter = new MyCursorAdapter(this, cursor);
            lvUebungen.setAdapter(adapter);
        }
    }
}
