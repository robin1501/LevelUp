package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class UebungenActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    ListView lvUebungen;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uebungen);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        lvUebungen = (ListView)findViewById(R.id.listUebungen);

        String query = "SELECT beschreibung FROM uebungen;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() != 0)
        {
            db.delete("uebungen", null, null);
            final String INSERT_UEBUNG = "INSERT INTO uebungen "
                    + "(beschreibung, bild, info) VALUES "
                    + "('Bankdrücken', '', ''), "
                    + "('Sit Ups', '', 'Beine anwinkeln'), "
                    + "('Beinpresse', '', 'Winkel auf 40 Grad stellen'), "
                    + "('Pull Ups', '', '');";

            //db.rawQuery(INSERT_UEBUNG, null);
            ContentValues values = new ContentValues();
            values.put("beschreibung", "Bankdrücken");
            values.put("bild", "");
            values.put("info", "");
            db.insert("uebungen", null, values);
            values.clear();
            values.put("beschreibung", "Sit Ups");
            values.put("bild", "");
            values.put("info", "Beine anwinkeln");
            db.insert("uebungen", null, values);
            values.clear();
            values.put("beschreibung", "Beinpresse");
            values.put("bild", "");
            values.put("info", "Winkel auf 40 Grad stellen");
            db.insert("uebungen", null, values);
            values.clear();
            values.put("beschreibung", "Pull Ups");
            values.put("bild", "");
            values.put("info", "");
            db.insert("uebungen", null, values);
            cursor = db.rawQuery(query, null);
        }

        ArrayList<String> listeUebungen = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            listeUebungen.add(cursor.getString(0));
            cursor.moveToNext();
        }

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listeUebungen);
        lvUebungen.setAdapter(adapter);

        cursor.close();
        db.close();

        lvUebungen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(UebungenActivity.this, UebungenDetail.class));
            }
        });

        Button neueUebung = (Button)findViewById(R.id.neueUebung);
        neueUebung.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(UebungenActivity.this, UebungenDetail.class));
            }
        });
	}
}
