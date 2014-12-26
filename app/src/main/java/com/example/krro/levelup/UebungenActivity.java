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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class UebungenActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    ListView lvUebungen;
    ArrayList<Integer> arrID;
    ArrayList<String> arrBeschreibung;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uebungen);

        setListView();

        lvUebungen.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent uebungDetail = new Intent(UebungenActivity.this, UebungenDetail.class);
                uebungDetail.putExtra("neu", false);
                uebungDetail.putExtra("id", arrID.get(i));
                uebungDetail.putExtra("beschreibung", arrBeschreibung.get(i));
                startActivity(uebungDetail);
            }
        });

        Button neueUebung = (Button)findViewById(R.id.neueUebung);
        neueUebung.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UebungenActivity.this);

                alert.setTitle("Neue Übung eingeben");

                final EditText input = new EditText(UebungenActivity.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String uebung = input.getText().toString();

                        Intent uebungDetail = new Intent(UebungenActivity.this, UebungenDetail.class);
                        uebungDetail.putExtra("neu", true);
                        uebungDetail.putExtra("beschreibung",uebung);
                        startActivity(uebungDetail);
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

    public void setListView()
    {
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        lvUebungen = (ListView)findViewById(R.id.listUebungen);

        String query = "SELECT u_id, beschreibung FROM uebungen;";
        Cursor cursor = db.rawQuery(query, null);

        arrID = new ArrayList<Integer>();
        arrBeschreibung = new ArrayList<String>();
        ArrayAdapter<String> adapter;

        cursor.moveToFirst();
        while(!cursor.isAfterLast())
        {
            arrID.add(cursor.getInt(0));
            arrBeschreibung.add(cursor.getString(1));
            cursor.moveToNext();
        }

        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                arrBeschreibung);
        lvUebungen.setAdapter(adapter);

        cursor.close();
        db.close();
    }
}
