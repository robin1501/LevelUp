package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class WorkoutActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    ListView lvWorkout;
    ArrayList<Integer> arrID;
    ArrayList<String> arrBeschreibung;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workout);

        setListView();

        lvWorkout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent workoutDetail = new Intent(WorkoutActivity.this, WorkoutDetail.class);
                workoutDetail.putExtra("neu", false);
                workoutDetail.putExtra("id", arrID.get(i));
                workoutDetail.putExtra("beschreibung", arrBeschreibung.get(i));
                startActivity(workoutDetail);
            }
        });

        Button neuesWorkout = (Button)findViewById(R.id.neuesWorkout);
        neuesWorkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(WorkoutActivity.this);

                alert.setTitle("Neues WOrkout eingeben");

                final EditText input = new EditText(WorkoutActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String workout = input.getText().toString();

                        Intent workoutDetail = new Intent(WorkoutActivity.this, WorkoutDetail.class);
                        workoutDetail.putExtra("neu", true);
                        workoutDetail.putExtra("beschreibung",workout);
                        startActivity(workoutDetail);
                    }
                });

                alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setListView();
    }

    private void setListView()
    {
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        lvWorkout = (ListView)findViewById(R.id.listWorkout);

        String query = "SELECT wh_id, beschreibung FROM workouthead;";
        Cursor cursor = db.rawQuery(query, null);

        arrID = new ArrayList<Integer>();
        arrBeschreibung = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                arrID.add(cursor.getInt(0));
                arrBeschreibung.add(cursor.getString(1));
                cursor.moveToNext();
            }

            adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1,
                    arrBeschreibung);
            lvWorkout.setAdapter(adapter);
        }

        cursor.close();
        db.close();
    }
}
