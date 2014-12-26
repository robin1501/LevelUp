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
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MyCursorAdapter extends CursorAdapter{

    private LayoutInflater cursorInflater;

    private ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();

    public MyCursorAdapter(Context context, Cursor c) {
        super(context, c);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        for (int i = 0; i < c.getCount(); i++) {
            itemChecked.add(i, false);
        }

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return cursorInflater.inflate(R.layout.uebungenliste, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, final Cursor cursor) {
        TextView beschreibung = (TextView)view.findViewById(R.id.tvBeschreibung);
        beschreibung.setText(cursor.getString(1));

        ImageView imgBild = (ImageView)view.findViewById(R.id.ivUebung);
        byte[] bild = cursor.getBlob(2);
        if (bild != null)
        {
            ByteArrayInputStream in = new ByteArrayInputStream(bild);
            Bitmap bmpBild = BitmapFactory.decodeStream(in);
            imgBild.setImageBitmap(bmpBild);
        }
        else
        {
            imgBild.setImageResource(R.drawable.noimage);
        }

        TextView muskelgruppen = (TextView)view.findViewById(R.id.tvMuskelgruppen);
        String strMuskelgruppen = "";
        int bauch = cursor.getInt(3);
        if(bauch == 1)
        {
            strMuskelgruppen = "Bauch, ";
        }
        int bizeps = cursor.getInt(4);
        if(bizeps == 1)
        {
            strMuskelgruppen += "Bizeps, ";
        }
        int trizeps = cursor.getInt(5);
        if(trizeps == 1)
        {
            strMuskelgruppen += "Trizeps, ";
        }
        int brust = cursor.getInt(6);
        if(brust == 1)
        {
            strMuskelgruppen += "Brust, ";
        }
        int schulter = cursor.getInt(7);
        if(schulter== 1)
        {
            strMuskelgruppen += "Schulter, ";
        }
        int ruecken = cursor.getInt(8);
        if(ruecken == 1)
        {
            strMuskelgruppen += "Rücken, ";
        }
        int beine = cursor.getInt(9);
        if(beine == 1)
        {
            strMuskelgruppen += "Beine, ";
        }
        muskelgruppen.setText(strMuskelgruppen.substring(0, strMuskelgruppen.length()-2));

        final CheckBox cbAuswahl = (CheckBox)view.findViewById(R.id.cbAuswahl);
        final int pos = cursor.getPosition();
        int checked = cursor.getInt(10);
        if(checked == 1)
        {
            itemChecked.set(pos, true);
        }
        cbAuswahl.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                CheckBox cb = (CheckBox) v.findViewById(R.id.cbAuswahl);

                if (cb.isChecked()) {
                    itemChecked.set(pos, true);
                } else if (!cb.isChecked()) {
                    itemChecked.set(pos, false);
                }
            }
        });
        cbAuswahl.setChecked(itemChecked.get(cursor.getPosition()));
    }

    public ArrayList<Boolean> getChecked()
    {
        return itemChecked;
    }
}
