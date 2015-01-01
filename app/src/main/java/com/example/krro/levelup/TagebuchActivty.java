package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;


public class TagebuchActivty extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    TextView tvDatum;
    ImageView ivDelete;
    ImageView ivTagebucheintrag;
    ImageView ivBild;
    ImageView back;
    ImageView next;
    TextView tvGewicht;
    TextView tvSchulterumfang;
    TextView tvArmumfang;
    TextView tvBrustumfang;
    TextView tvBauchumfang;
    TextView tvHueftumfang;
    TextView tvBeinumfang;

    String query;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagebuch);

        tvDatum= (TextView)findViewById(R.id.tvDatum);
        ivDelete = (ImageView)findViewById(R.id.ivDelete);
        ivTagebucheintrag = (ImageView)findViewById(R.id.ivTagebucheintrag);
        ivBild = (ImageView)findViewById(R.id.ivBild);
        back = (ImageView)findViewById(R.id.ivBack);
        next = (ImageView)findViewById(R.id.ivNext);
        tvGewicht = (TextView)findViewById(R.id.tvGewicht);
        tvSchulterumfang = (TextView)findViewById(R.id.tvSchulterumfang);
        tvArmumfang = (TextView)findViewById(R.id.tvArmumfang);
        tvBrustumfang = (TextView)findViewById(R.id.tvBrustumfang);
        tvBauchumfang = (TextView)findViewById(R.id.tvBauchumfang);
        tvHueftumfang = (TextView)findViewById(R.id.tvHueftumfang);
        tvBeinumfang = (TextView)findViewById(R.id.tvBeinumfang);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        query = "SELECT _id, datum, bild, gewicht, schulterumfang, armumfang, "
                + "brustumfang, bauchumfang, hueftumfang, beinumfang "
                + "FROM tagebuch";
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        setData();

        ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());

                alert.setTitle("Sind Sie sich sicher, dass Sie diesen Eintrag löschen wollen?");


                alert.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        db.delete("tagebuch", "_id = " + id, null);

                        cursor = db.rawQuery(query, null);
                        if(cursor.getCount() != 0) {
                            cursor.moveToLast();
                            setData();
                        }
                    }
                });

                alert.setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });

        ivTagebucheintrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tagebucheintragActivity = new Intent(TagebuchActivty.this, TagebucheintragActivity.class);
                startActivity(tagebucheintragActivity);
            }
        });

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        cursor = db.rawQuery(query, null);
        cursor.moveToLast();
        setData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cursor.close();
        db.close();
    }

    private void setData() {

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

        id = cursor.getInt(0);

        String datum = cursor.getString(1);
        tvDatum.setText(datum);

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
        tvGewicht.setText(gewicht+"");

        int schulterumfang = cursor.getInt(4);
        tvSchulterumfang.setText(schulterumfang+"");

        int armumfang = cursor.getInt(5);
        tvArmumfang.setText(armumfang+"");

        int brustumfang = cursor.getInt(6);
        tvBrustumfang.setText(brustumfang+"");

        int bauchumfang = cursor.getInt(7);
        tvBauchumfang.setText(bauchumfang+"");

        int hueftumfang = cursor.getInt(8);
        tvHueftumfang.setText(hueftumfang+"");

        int beinumfang = cursor.getInt(9);
        tvBeinumfang.setText(beinumfang+"");
    }
}
