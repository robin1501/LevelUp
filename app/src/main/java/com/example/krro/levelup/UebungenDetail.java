package com.example.krro.levelup;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class UebungenDetail extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    ImageView imgBild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uebungdetail);

        Intent uebungDetail = getIntent();
        Integer id = uebungDetail.getIntExtra("id", 0);
        String beschreibung = uebungDetail.getStringExtra("beschreibung");
        String bild = uebungDetail.getStringExtra("bild");
        Bitmap bmpBild = BitmapFactory.decodeFile("res/drawable/noimage.jpg");
        String info = uebungDetail.getStringExtra("info");

        TextView tvUebung = (TextView)findViewById(R.id.tvUebung);
        imgBild = (ImageView)findViewById(R.id.imgUebung);
        EditText txtInfo = (EditText)findViewById(R.id.txtInfo);

        tvUebung.setText(beschreibung);
        imgBild.setImageBitmap(bmpBild);
        txtInfo.setText(info);

        imgBild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
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
