package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfilActivity extends Activity {

    private DBHelper dbHelper = new DBHelper(getApplication());
    private SQLiteDatabase db = dbHelper.getWritableDatabase();



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);
        Button save = (Button)findViewById(R.id.angry_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                ContentValues args = new ContentValues();
                args.put("name", R.id.editTextName);
                args.put("age", R.id.editTextAlter);
                args.put("gewicht", R.id.editTextGewicht);
                args.put("groesse", R.id.editTextGröße);
                //args.put("geschlecht", R.id.editTextGeschlecht);
                args.put("wunschgewicht", R.id.editTextWunschgewicht);
                db.update("profil",args,"p_id", null );
            }
        });
	}
}
