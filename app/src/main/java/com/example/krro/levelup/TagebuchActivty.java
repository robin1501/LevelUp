package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagebuch);

        tvDatum= (TextView)findViewById(R.id.tvDatum);
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

        query = "SELECT datum, bild, gewicht, schulterumfang, armumfang, "
                + "brustumfang, bauchumfang, hueftumfang, beinumfang "
                + "FROM tagebuch";
        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        setData();

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

        String datum = cursor.getString(0);
        tvDatum.setText(datum);

        byte[] bild = cursor.getBlob(1);
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

        int gewicht = cursor.getInt(2);
        tvGewicht.setText(gewicht+"");

        int schulterumfang = cursor.getInt(3);
        tvSchulterumfang.setText(schulterumfang+"");

        int armumfang = cursor.getInt(4);
        tvArmumfang.setText(armumfang+"");

        int brustumfang = cursor.getInt(5);
        tvBrustumfang.setText(brustumfang+"");

        int bauchumfang = cursor.getInt(6);
        tvBauchumfang.setText(bauchumfang+"");

        int hueftumfang = cursor.getInt(7);
        tvHueftumfang.setText(hueftumfang+"");

        int beinumfang = cursor.getInt(8);
        tvBeinumfang.setText(beinumfang+"");
    }
}
