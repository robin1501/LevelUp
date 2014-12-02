package com.example.krro.levelup;

import android.app.Activity;
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

import java.net.URI;

public class UebungenDetail extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imgBild;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uebungdetail);

        Intent uebungDetail = getIntent();
        final boolean neueUebung = uebungDetail.getBooleanExtra("neu", false);

        String beschreibung;
        String bild;
        Integer bauch;
        Integer bizeps;
        Integer trizeps;
        Integer brust;
        Integer schulter;
        Integer ruecken;
        Integer beine;
        String info;

        TextView tvUebung = (TextView) findViewById(R.id.tvUebung);
        imgBild = (ImageView) findViewById(R.id.imgUebung);
        imgBild.setImageResource(R.drawable.noimage);
        CheckBox cbBauch = (CheckBox)findViewById(R.id.cbBauch);
        CheckBox cbBizeps = (CheckBox)findViewById(R.id.cbBauch);
        CheckBox cbTrizeps = (CheckBox)findViewById(R.id.cbBauch);
        CheckBox cbBrust = (CheckBox)findViewById(R.id.cbBauch);
        CheckBox cbSchulter = (CheckBox)findViewById(R.id.cbBauch);
        CheckBox cbRuecken = (CheckBox)findViewById(R.id.cbBauch);
        CheckBox cbBeine = (CheckBox)findViewById(R.id.cbBauch);
        EditText txtInfo = (EditText) findViewById(R.id.txtInfo);

        if(!neueUebung) {
            Integer id = uebungDetail.getIntExtra("id", 0);
            beschreibung = uebungDetail.getStringExtra("beschreibung");

            dbHelper = new DBHelper(getApplicationContext());
            db = dbHelper.getWritableDatabase();

            String query = "SELECT bild, bauch, bizeps, trizeps, brust, schulter, ruecken, beine, info FROM uebungen WHERE u_id = " + id;
            Cursor cursor = db.rawQuery(query, null);

            bild = cursor.getString(0);
            bauch = cursor.getInt(1);
            bizeps = cursor.getInt(2);
            trizeps = cursor.getInt(3);
            brust = cursor.getInt(4);
            schulter = cursor.getInt(5);
            ruecken = cursor.getInt(6);
            beine = cursor.getInt(7);
            info = cursor.getString(8);

            tvUebung.setText(beschreibung);
            if (!bild.equals(""))
            {
                Bitmap bmpBild = BitmapFactory.decodeFile(bild);
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
        else
        {

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
                if(neueUebung)
                {

                }
                else
                {

                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            imgBild.setImageBitmap(photo);
        }
    }
}
