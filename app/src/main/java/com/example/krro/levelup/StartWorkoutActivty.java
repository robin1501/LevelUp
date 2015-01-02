package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;


public class StartWorkoutActivty extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    TextView tvUebung;
    ImageView ivBild;
    ImageView back;
    ImageView next;
    TextView tvLetztesGewicht;
    EditText txtGewicht;
    TextView tvLetzteWiederholungen;
    EditText txtWiederholungen;
    TextView tvLetzteSaetze;
    EditText txtSaetze;

    int uebungID = 0;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startworkout);

        final Intent startWorkout = getIntent();
        final int terminID = startWorkout.getIntExtra("terminID", 0);
        final int workoutID = startWorkout.getIntExtra("workoutID", 0);

        tvUebung = (TextView)findViewById(R.id.tvUebung);
        ivBild = (ImageView)findViewById(R.id.ivBild);
        next = (ImageView)findViewById(R.id.ivNext);
        back = (ImageView)findViewById(R.id.ivBack);
        tvLetztesGewicht = (TextView)findViewById(R.id.tvGewicht);
        txtGewicht = (EditText)findViewById(R.id.txtGewicht);
        tvLetzteWiederholungen = (TextView)findViewById(R.id.tvWiederholungen);
        txtWiederholungen = (EditText)findViewById(R.id.txtWiederholungen);
        tvLetzteSaetze = (TextView)findViewById(R.id.tvSaetze);
        txtSaetze = (EditText)findViewById(R.id.txtSaetze);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        query = "SELECT u.u_id, u.beschreibung, u.bild, u.gewicht, u.wiederholungen, u.saetze "
                + "FROM uebungen u INNER JOIN workoutpos w ON u.u_id = w.u_id "
                + "WHERE w.w_id = " + workoutID + " "
                + "AND NOT EXISTS (SELECT t.t_id FROM todo_workoutpos t "
                + "WHERE t.t_id = " + terminID + " AND u.u_id = t.u_id)";
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        setData();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToNext();
                setData();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cursor.moveToPrevious();
                setData();
            }
        });

        Button btnAbschluss = (Button)findViewById(R.id.cbAbschluss);
        btnAbschluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                InputMethodManager imm = (InputMethodManager)getSystemService(
                        Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(txtSaetze.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(txtWiederholungen.getWindowToken(), 0);
                imm.hideSoftInputFromWindow(txtGewicht.getWindowToken(), 0);

                if (txtGewicht.getText().toString().equals("")
                        || txtWiederholungen.getText().toString().equals("")
                        || txtSaetze.getText().toString().equals(""))
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(StartWorkoutActivty.this);

                    alert.setTitle("Bitte alle Felder ausfüllen!");

                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.show();
                }
                else
                {
                    int gewicht = Integer.parseInt(txtGewicht.getText().toString());
                    int wiederholungen = Integer.parseInt(txtWiederholungen.getText().toString());
                    int saetze = Integer.parseInt(txtSaetze.getText().toString());

                    ContentValues values = new ContentValues();
                    values.put("gewicht", gewicht);
                    values.put("wiederholungen", wiederholungen);
                    values.put("saetze", saetze);
                    db.update("uebungen", values, "u_id = " + uebungID, null);

                    values.put("t_id", terminID);
                    values.put("u_id", uebungID);
                    db.insert("todo_workoutpos", null, values);

                    txtGewicht.setText("");
                    txtWiederholungen.setText("");
                    txtSaetze.setText("");

                    cursor = db.rawQuery(query, null);
                    if(cursor.getCount() == 0)
                    {
                        ContentValues updHead = new ContentValues();
                        updHead.put("abgeschlossen", 1);
                        db.update("todo_workouthead", updHead, "t_id = " + terminID, null);
                        StartWorkoutActivty.this.finish();
                    }
                    else {
                        cursor.moveToFirst();
                        setData();
                    }

                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cursor.close();
        db.close();
    }

    private void setData()
    {
        if(cursor.isFirst())
        {
            back.setVisibility(View.INVISIBLE);
        }
        else
        {
            back.setVisibility(View.VISIBLE);
        }

        if(cursor.isLast())
        {
            next.setVisibility(View.INVISIBLE);
        }
        else
        {
            next.setVisibility(View.VISIBLE);
        }

        uebungID = cursor.getInt(0);

        String beschreibung = cursor.getString(1);
        tvUebung.setText(beschreibung);

        byte[] bild = cursor.getBlob(2);
        if (bild != null)
        {
            ByteArrayInputStream in = new ByteArrayInputStream(bild);
            Bitmap bmpBild = BitmapFactory.decodeStream(in);
            ivBild.setImageBitmap(bmpBild);
        }
        else
        {
            ivBild.setImageResource(R.drawable.noimage);
        }

        int gewicht = cursor.getInt(3);
        tvLetztesGewicht.setText(gewicht+"");

        int wiederholungen = cursor.getInt(4);
        tvLetzteWiederholungen.setText(wiederholungen+"");

        int saetze = cursor.getInt(5);
        tvLetzteSaetze.setText(saetze+"");
    }

}
