package com.example.krro.levelup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class TagebucheintragActivity extends Activity {

    private static final int CAMERA_REQUEST = 1888;

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    TextView tvDatum;
    ImageView ivBild;
    EditText txtGewicht;
    EditText txtSchulterumfang;
    EditText txtArmumfang;
    EditText txtBrustumfang;
    EditText txtBauchumfang;
    EditText txtHueftumfang;
    EditText txtBeinumfang;

    byte[] bild = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tagebucheintrag);

        tvDatum= (TextView)findViewById(R.id.tvDatum);
        ivBild = (ImageView)findViewById(R.id.ivBild);
        txtGewicht = (EditText)findViewById(R.id.txtGewicht);
        txtSchulterumfang = (EditText)findViewById(R.id.txtSchulterumfang);
        txtArmumfang = (EditText)findViewById(R.id.txtArmumfang);
        txtBrustumfang = (EditText)findViewById(R.id.txtBrustumfang);
        txtBauchumfang = (EditText)findViewById(R.id.txtBauchumfang);
        txtHueftumfang = (EditText)findViewById(R.id.txtHueftumfang);
        txtBeinumfang = (EditText)findViewById(R.id.txtBeinumfang);

        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter= new SimpleDateFormat("dd.MM.yyyy");
        final String datum = formatter.format(currentDate.getTime());

        tvDatum.setText(datum);
        ivBild.setImageResource(R.drawable.noimage);

        ivBild.setOnClickListener(new View.OnClickListener() {
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
                dbHelper = new DBHelper(getApplicationContext());
                db = dbHelper.getWritableDatabase();

                String gewicht = txtGewicht.getText().toString();
                String schulterumfang = txtSchulterumfang.getText().toString();
                String armumfang = txtArmumfang.getText().toString();
                String brustumfang = txtBrustumfang.getText().toString();
                String bauchumfang = txtBauchumfang.getText().toString();
                String hueftumfang = txtHueftumfang.getText().toString();
                String beinumfang = txtBeinumfang.getText().toString();

                if(     gewicht.isEmpty() ||
                        schulterumfang.isEmpty() ||
                        armumfang.isEmpty() ||
                        brustumfang.isEmpty() ||
                        bauchumfang.isEmpty() ||
                        hueftumfang.isEmpty() ||
                        beinumfang.isEmpty())
                {
                    AlertDialog.Builder alert = new AlertDialog.Builder(TagebucheintragActivity.this);

                    alert.setTitle("Bitte alle Felder ausfüllen!");

                    alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                        }
                    });

                    alert.show();
                }
                else
                {
                    ContentValues insTagebuch = new ContentValues();
                    insTagebuch.put("datum", datum);
                    if(bild != null) {
                        insTagebuch.put("bild", bild);
                    }
                    insTagebuch.put("gewicht", gewicht);
                    insTagebuch.put("schulterumfang", schulterumfang);
                    insTagebuch.put("armumfang", armumfang);
                    insTagebuch.put("brustumfang", brustumfang);
                    insTagebuch.put("bauchumfang", bauchumfang);
                    insTagebuch.put("hueftumfang", hueftumfang);
                    insTagebuch.put("beinumfang", beinumfang);

                    db.insert("tagebuch", null, insTagebuch);

                    TagebucheintragActivity.this.finish();
                    Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bild = stream.toByteArray();

            ivBild.setImageBitmap(photo);
        }
    }
}
