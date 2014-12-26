package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UebungenDetail extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imgBild;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    Integer id;
    String beschreibung;
    byte[] bild = null;
    Integer bauch;
    Integer bizeps;
    Integer trizeps;
    Integer brust;
    Integer schulter;
    Integer ruecken;
    Integer beine;
    String info;

    TextView tvUebung;
    CheckBox cbBauch;
    CheckBox cbBizeps;
    CheckBox cbTrizeps;
    CheckBox cbBrust;
    CheckBox cbSchulter;
    CheckBox cbRuecken;
    CheckBox cbBeine;
    EditText txtInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uebungdetail);

        Button grafik = (Button) findViewById(R.id.grafik);
        grafik.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // start activity with intent
                Intent intent = new Intent(UebungenDetail.this,
                        Grafik.class);
              //  intent.putExtra(SecondActivity.EXTRA_COUNT, mCounter);
                startActivityForResult(intent, 0);
            }
        });


        Intent uebungDetail = getIntent();
        final boolean neueUebung = uebungDetail.getBooleanExtra("neu", false);

        tvUebung = (TextView) findViewById(R.id.tvUebung);
        imgBild = (ImageView) findViewById(R.id.imgUebung);
        imgBild.setImageResource(R.drawable.noimage);
        cbBauch = (CheckBox)findViewById(R.id.cbBauch);
        cbBizeps = (CheckBox)findViewById(R.id.cbBizeps);
        cbTrizeps = (CheckBox)findViewById(R.id.cbTrizeps);
        cbBrust = (CheckBox)findViewById(R.id.cbBrust);
        cbSchulter = (CheckBox)findViewById(R.id.cbSchulter);
        cbRuecken = (CheckBox)findViewById(R.id.cbRuecken);
        cbBeine = (CheckBox)findViewById(R.id.cbBeine);
        txtInfo = (EditText) findViewById(R.id.txtInfo);

        beschreibung = uebungDetail.getStringExtra("beschreibung");
        tvUebung.setText(beschreibung);
        tvUebung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(UebungenDetail.this);

                alert.setTitle(R.string.neueBez);

                final EditText input = new EditText(UebungenDetail.this);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        beschreibung = input.getText().toString();
                        tvUebung.setText(beschreibung);
                    }
                });

                alert.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        if(!neueUebung)
        {
            id = uebungDetail.getIntExtra("id", 0);

            String query = "SELECT bild, bauch, bizeps, trizeps, brust, schulter, ruecken, beine, info FROM uebungen WHERE u_id = " + id;
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();

            bild = cursor.getBlob(0);
            bauch = cursor.getInt(1);
            bizeps = cursor.getInt(2);
            trizeps = cursor.getInt(3);
            brust = cursor.getInt(4);
            schulter = cursor.getInt(5);
            ruecken = cursor.getInt(6);
            beine = cursor.getInt(7);
            info = cursor.getString(8);

            if (bild != null)
            {
                ByteArrayInputStream in = new ByteArrayInputStream(bild);
                Bitmap bmpBild = BitmapFactory.decodeStream(in);
                imgBild.setImageBitmap(bmpBild);
            }

            if (bauch == 0)
            {
                cbBauch.setChecked(false);
            }
            else
            {
                cbBauch.setChecked(true);
            }
            if (bizeps == 0)
            {
                cbBizeps.setChecked(false);
            }
            else
            {
                cbBizeps.setChecked(true);
            }
            if (trizeps == 0)
            {
                cbTrizeps.setChecked(false);
            }
            else
            {
                cbTrizeps.setChecked(true);
            }
            if (brust == 0)
            {
                cbBrust.setChecked(false);
            }
            else
            {
                cbBrust.setChecked(true);
            }
            if (schulter == 0)
            {
                cbSchulter.setChecked(false);
            }
            else
            {
                cbSchulter.setChecked(true);
            }
            if (ruecken == 0)
            {
                cbRuecken.setChecked(false);
            }
            else
            {
                cbRuecken.setChecked(true);
            }
            if (beine == 0)
            {
                cbBeine.setChecked(false);
            }
            else
            {
                cbBeine.setChecked(true);
            }
            txtInfo.setText(info);
        }

        imgBild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Button btnSpeichern = (Button)findViewById(R.id.btnSpeichern);
        btnSpeichern.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                values.put("beschreibung", beschreibung);
                if(bild != null) {
                    values.put("bild", bild);
                }

                if (cbBauch.isChecked())
                {
                    values.put("bauch", 1);
                }
                else
                {
                    values.put("bauch", 0);
                }

                if (cbBizeps.isChecked())
                {
                    values.put("bizeps", 1);
                }
                else
                {
                    values.put("bizeps", 0);
                }

                if (cbTrizeps.isChecked())
                {
                    values.put("trizeps", 1);
                }
                else
                {
                    values.put("trizeps", 0);
                }

                if (cbBrust.isChecked())
                {
                    values.put("brust", 1);
                }
                else
                {
                    values.put("brust", 0);
                }

                if (cbSchulter.isChecked())
                {
                    values.put("schulter", 1);
                }
                else
                {
                    values.put("schulter", 0);
                }

                if (cbRuecken.isChecked())
                {
                    values.put("ruecken", 1);
                }
                else
                {
                    values.put("ruecken", 0);
                }

                if (cbBeine.isChecked())
                {
                    values.put("beine", 1);
                }
                else
                {
                    values.put("beine", 0);
                }

                values.put("info", txtInfo.getText().toString());

                if(neueUebung)
                {
                    db.insert("uebungen", null, values);
                    UebungenDetail.this.finish();
                }
                else
                {
                    db.update("uebungen", values, "u_id = " + id, null);
                    UebungenDetail.this.finish();
                }
                Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bild = stream.toByteArray();

            imgBild.setImageBitmap(photo);
        }
    }
}
