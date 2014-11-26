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

        String query = "SELECT u_id, beschreibung, bild, info FROM uebungen;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.getCount() == 0)
        {
            //db.delete("uebungen", null, null);

            ContentValues values = new ContentValues();
            values.put("beschreibung", "Bankdrücken");
            values.put("bild", "@drawable/noimage.jpg");
            values.put("info", "Test Bankdrücken");
            db.insert("uebungen", null, values);
            values.put("beschreibung", "Sit Ups");
            values.put("bild", "@drawable/noimage.jpg");
            values.put("info", "Beine anwinkeln");
            db.insert("uebungen", null, values);
            values.put("beschreibung", "Beinpresse");
            values.put("bild", "@drawable/noimage.jpg");
            values.put("info", "Winkel auf 40 Grad stellen");
            db.insert("uebungen", null, values);
            values.put("beschreibung", "Pull Ups");
            values.put("bild", "@drawable/noimage.jpg");
            values.put("info", "Test Pull Up");
            db.insert("uebungen", null, values);
            cursor = db.rawQuery(query, null);
        }

        final ArrayList<Integer> arrID = new ArrayList<Integer>();
        final ArrayList<String> arrBeschreibung = new ArrayList<String>();
        final ArrayList<String> arrBild = new ArrayList<String>();
        final ArrayList<String> arrInfo = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            arrID.add(cursor.getInt(0));
            arrBeschreibung.add(cursor.getString(1));
            arrBild.add(cursor.getString(2));
            arrInfo.add(cursor.getString(3));
            cursor.moveToNext();
        }

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                arrBeschreibung);
        lvUebungen.setAdapter(adapter);

        cursor.close();
        db.close();

        lvUebungen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent uebungDetail = new Intent(UebungenActivity.this, UebungenDetail.class);
                uebungDetail.putExtra("id", arrID.get(i));
                uebungDetail.putExtra("beschreibung", arrBeschreibung.get(i));
                uebungDetail.putExtra("bild", arrBild.get(i));
                uebungDetail.putExtra("info", arrInfo.get(i));
                startActivity(uebungDetail);
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
