package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfilActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    Cursor cursor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);

        Spinner spinner = (Spinner)findViewById(R.id.spGeschlecht);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arrGeschlecht, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
            Spinner geschlecht = (Spinner)findViewById(R.id.spGeschlecht);
            for (int i=0;i<geschlecht.getCount();i++) {
                if (geschlecht.getItemAtPosition(i).equals(cursor.getString(4))) {
                    geschlecht.setSelection(i);
                }
            }
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
                Spinner geschlecht = (Spinner)findViewById(R.id.spGeschlecht);
                EditText wunschgewicht = (EditText)findViewById(R.id.editTextWunschgewicht);

                ContentValues args = new ContentValues();
                args.put("name", name.getText().toString());
                args.put("age", alter.getText().toString());
                args.put("gewicht", gewicht.getText().toString());
                args.put("groesse", groesse.getText().toString());
                args.put("geschlecht", geschlecht.getSelectedItem().toString());
                args.put("wunschgewicht", wunschgewicht.getText().toString());


                if(cursor.getCount() == 0)
                {
                    db.insert("profil", null, args);
                }
                else {
                    int ret = db.update("profil", args, "p_id = 1", null);
                    if (ret != 0) {
                        Toast.makeText(getApplicationContext(), "Änderungen gespeichert", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
	}
}
