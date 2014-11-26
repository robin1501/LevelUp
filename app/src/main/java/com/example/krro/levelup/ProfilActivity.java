package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();



        String query = "SELECT name, age, gewicht, groesse, geschlecht, wunschgewicht FROM profil";
        cursor = db.rawQuery(query, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            EditText name = (EditText)findViewById(R.id.editTextName);
            name.setText(cursor.getString(0));
            EditText alter = (EditText)findViewById(R.id.editTextAlter);
            alter.setText(cursor.getString(1));
            EditText gewicht = (EditText)findViewById(R.id.editTextGewicht);
            gewicht.setText(cursor.getString(2));
            EditText groesse = (EditText)findViewById(R.id.editTextGröße);
            groesse.setText(cursor.getString(3));
            //EditText geschlecht = (EditText)findViewById(R.id.editTextGeschlecht);
            //geschlecht.setText(cursor.getString(4));
            EditText wunschgewicht = (EditText)findViewById(R.id.editTextWunschgewicht);
            wunschgewicht.setText(cursor.getString(5));

        }



        Button save = (Button)findViewById(R.id.angry_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                EditText name = (EditText)findViewById(R.id.editTextName);
                EditText alter = (EditText)findViewById(R.id.editTextAlter);
                EditText gewicht = (EditText)findViewById(R.id.editTextGewicht);
                EditText groesse = (EditText)findViewById(R.id.editTextGröße);
                //EditText geschlecht = (EditText)findViewById(R.id.editTextGeschlecht);
                EditText wunschgewicht = (EditText)findViewById(R.id.editTextWunschgewicht);

                ContentValues args = new ContentValues();
                args.put("name", name.getText().toString());
                args.put("age", alter.getText().toString());
                args.put("gewicht", gewicht.getText().toString());
                args.put("groesse", groesse.getText().toString());
                //args.put("geschlecht", geschlecht.getText().toString());
                args.put("wunschgewicht", wunschgewicht.getText().toString());


                if(cursor.getCount() == 0)
                {
                    db.insert("profil", null, args);
                }
                else {
                    db.update("profil", args, "p_id = 1", null);
                    Toast.makeText(getApplicationContext(), "Änderungen gespeichert",Toast.LENGTH_SHORT).show();
                }
            }
        });
	}
}
