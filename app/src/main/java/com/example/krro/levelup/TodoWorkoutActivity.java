package com.example.krro.levelup;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class TodoWorkoutActivity extends Activity{

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    TerminAdapter adapter;

    ListView lvTermine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_workout);

        setListView();

        Button btnNeuerTermin = (Button)findViewById(R.id.btnNeuerTermin);
        btnNeuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent todoDetail = new Intent(TodoWorkoutActivity.this, TodoWorkoutDetail.class);
                startActivity(todoDetail);
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
        lvTermine = (ListView)findViewById(R.id.listTermine);

        String query = "SELECT t.t_id AS _id, t.w_id, t.datum, w.beschreibung, t.kalendereintrag "
            + "FROM todo_workouthead t INNER JOIN workouthead w ON t.w_id = w.w_id "
            + "WHERE abgeschlossen = 0 "
            + "ORDER BY SUBSTR(t.datum, 7, 4), SUBSTR(t.datum, 4, 2), SUBSTR(t.datum, 1, 2)";
        cursor = db.rawQuery(query, null);

        if(cursor.getCount() != 0) {
            adapter = new TerminAdapter(this, cursor);
            lvTermine.setAdapter(adapter);
        }

    }
}
