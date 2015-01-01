package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class ProfilActivity extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    Cursor cursor;

    EditText name;
    EditText alter;
    EditText gewicht;
    EditText groesse;
    Spinner geschlecht;
    EditText wunschgewicht;
    EditText workouts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);


        name = (EditText)findViewById(R.id.editTextName);
        alter = (EditText)findViewById(R.id.editTextAlter);
        gewicht = (EditText)findViewById(R.id.editTextGewicht);
        groesse = (EditText)findViewById(R.id.editTextGröße);
        geschlecht = (Spinner)findViewById(R.id.spGeschlecht);
        wunschgewicht = (EditText)findViewById(R.id.editTextWunschgewicht);
        workouts = (EditText)findViewById(R.id.editTextWorkouts);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.arrGeschlecht, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        geschlecht.setAdapter(adapter);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        final String query = "SELECT name, age, gewicht, groesse, geschlecht, wunschgewicht, workouts FROM profil";
        cursor = db.rawQuery(query, null);

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();

            name.setText(cursor.getString(0));
            alter.setText(cursor.getString(1));
            gewicht.setText(cursor.getString(2));
            groesse.setText(cursor.getString(3));
            for (int i=0;i<geschlecht.getCount();i++) {
                if (geschlecht.getItemAtPosition(i).equals(cursor.getString(4))) {
                    geschlecht.setSelection(i);
                }
            }
            wunschgewicht.setText(cursor.getString(5));
            workouts.setText(cursor.getString(6));

        }

        ImageView tagebuch = (ImageView)findViewById(R.id.ivTagebuch);
        tagebuch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cntTagebuch = "SELECT _id FROM tagebuch";
                Cursor c = db.rawQuery(cntTagebuch, null);

                Intent tagebuchActivity;

                if(c.getCount() == 0)
                {
                    tagebuchActivity = new Intent(ProfilActivity.this, TagebucheintragActivity.class);
                }
                else
                {
                    tagebuchActivity = new Intent(ProfilActivity.this, TagebuchActivty.class);
                }

                startActivity(tagebuchActivity);
            }
        });

        Button save = (Button)findViewById(R.id.angry_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

                ContentValues values = new ContentValues();
                values.put("name", name.getText().toString());
                values.put("age", alter.getText().toString());
                values.put("gewicht", gewicht.getText().toString());
                values.put("groesse", groesse.getText().toString());
                values.put("geschlecht", geschlecht.getSelectedItem().toString());
                values.put("wunschgewicht", wunschgewicht.getText().toString());
                values.put("workouts", workouts.getText().toString());


                if(cursor.getCount() == 0)
                {
                    db.insert("profil", null, values);
                }
                else {
                    db.update("profil", values, "p_id = 1", null);
                }
                Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
            }
        });
	}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
