package com.example.krro.levelup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.ArrayList;

public class UebungenActivity extends ListActivity {

    private DBHelper dbHelper = new DBHelper(getApplication());
    private SQLiteDatabase db = dbHelper.getReadableDatabase();

    ArrayList<String> listeUebungen = new ArrayList<String>();
    ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uebungen);

        String query = "SELECT beschreibung FROM uebungen;";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            listeUebungen.add(cursor.getString(0));
            cursor.moveToNext();
        }

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listeUebungen);
        setListAdapter(adapter);

        cursor.close();
        db.close();

        Button neueUebung = (Button)findViewById(R.id.neueUebung);
        neueUebung.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(UebungenActivity.this, UebungenDetail.class));
            }
        });
	}
}
