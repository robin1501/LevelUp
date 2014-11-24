package com.example.krro.levelup;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

public class UebungenActivity extends Activity {

    private DBHelper dbHelper = new DBHelper(getApplication());
    private SQLiteDatabase db;

    public void open() throws SQLException {
        db = dbHelper.getReadableDatabase();
    }



    ArrayList<String> listeUebungen = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    ListView lvUebungen = (ListView)findViewById(R.id.listUebungen);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uebungen);

        try {
            open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
