package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class ProfilActivity extends Activity {

 //   private DBHelper dbHelper = new DBHelper(getApplication());
 // private SQLiteDatabase db = dbHelper.getWritableDatabase();
 private DBHelper dbHelper;
    private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        Button save = (Button)findViewById(R.id.angry_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                ContentValues args = new ContentValues();
                args.put("name", R.id.editTextName);
                db.insert("profil", null, args);
                args.put("age", R.id.editTextAlter);
                db.insert("profil", null, args);
                args.put("gewicht", R.id.editTextGewicht);
                db.insert("profil", null, args);
                args.put("groesse", R.id.editTextGröße);
                db.insert("profil", null, args);
                //args.put("geschlecht", R.id.editTextGeschlecht);
                //db.insert("profil", null, args);
                args.put("wunschgewicht", R.id.editTextWunschgewicht);
                db.insert("profil", null, args);
                db.update("profil",args,"p_id", null );
            }
        });

	}
}
