package com.example.krro.levelup;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;

public class MyCursorAdapter extends CursorAdapter{

    private LayoutInflater cursorInflater;

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.uebungenliste, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView beschreibung = (TextView)view.findViewById(R.id.tvBeschreibung);
        beschreibung.setText(cursor.getString(1));
        byte[] bild = cursor.getBlob(2);
        if (bild != null)
        {
            ByteArrayInputStream in = new ByteArrayInputStream(bild);
            Bitmap bmpBild = BitmapFactory.decodeStream(in);
            ImageView imgBild = (ImageView)view.findViewById(R.id.ivUebung);
            imgBild.setImageBitmap(bmpBild);
        }
        TextView muskelgruppen = (TextView)view.findViewById(R.id.tvMuskelgruppen);
        String strMuskelgruppen = "";
        int bauch = cursor.getInt(3);
        if(bauch == 1)
        {
            strMuskelgruppen = "Bauch";
        }
        int bizeps = cursor.getInt(4);
        if(bizeps == 1)
        {
            if(strMuskelgruppen!="")
            {
                strMuskelgruppen += ", ";
            }
            strMuskelgruppen += "Bizeps";
        }
        int trizeps = cursor.getInt(5);
        if(trizeps == 1)
        {
            if(strMuskelgruppen!="")
            {
                strMuskelgruppen += ", ";
            }
            strMuskelgruppen += "Trizeps";
        }
        int brust = cursor.getInt(6);
        if(brust == 1)
        {
            if(strMuskelgruppen!="")
            {
                strMuskelgruppen += ", ";
            }
            strMuskelgruppen += "Brust";
        }
        int schulter = cursor.getInt(7);
        if(schulter== 1)
        {
            if(strMuskelgruppen!="")
            {
                strMuskelgruppen += ", ";
            }
            strMuskelgruppen += "Schulter";
        }
        int ruecken = cursor.getInt(8);
        if(ruecken == 1)
        {
            if(strMuskelgruppen!="")
            {
                strMuskelgruppen += ", ";
            }
            strMuskelgruppen += "Rücken";
        }
        int beine = cursor.getInt(9);
        if(beine == 1)
        {
            if(strMuskelgruppen!="")
            {
                strMuskelgruppen += ", ";
            }
            strMuskelgruppen += "Beine";
        }
        muskelgruppen.setText(strMuskelgruppen);
        CheckBox cb = (CheckBox)view.findViewById(R.id.cbAuswahl);
        cb.setChecked(false);
    }
}
