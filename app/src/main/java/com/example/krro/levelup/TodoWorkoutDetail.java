package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;


public class TodoWorkoutDetail extends Activity {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    ArrayList<Integer> arrID = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_neu);

        Spinner spinner = (Spinner)findViewById(R.id.spWorkout);

        String query = "SELECT w_id, beschreibung from workouthead";

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();
        cursor = db.rawQuery(query, null);

        List<String> lBez = new ArrayList<String>();

        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                arrID.add(cursor.getInt(0));
                lBez.add(cursor.getString(1));
                cursor.moveToNext();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lBez);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        Button btnNeuerTermin = (Button)findViewById(R.id.btnTerminErstellen);
        btnNeuerTermin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spWorkout = (Spinner)findViewById(R.id.spWorkout);
                DatePicker dpDatum = (DatePicker)findViewById(R.id.dpDatum);
                TimePicker tpZeit = (TimePicker)findViewById(R.id.tpZeit);
                CheckBox cbKalendereintrag = (CheckBox)findViewById(R.id.cbKalendereintrag);

                ContentValues values = new ContentValues();
                values.put("w_id", arrID.get(spWorkout.getSelectedItemPosition()));
                int jahr = dpDatum.getYear();
                int monat = dpDatum.getMonth();
                String strMonat = (monat+1) + "";
                if(monat < 10)
                {
                    strMonat = "0" + (monat+1);
                }
                int tag = dpDatum.getDayOfMonth();
                String strTag = tag + "";
                if(tag < 10)
                {
                    strTag = "0" + tag;
                }
                values.put("datum", strTag + "." + strMonat + "." + jahr);
                int stunden = tpZeit.getCurrentHour();
                int minuten = tpZeit.getCurrentMinute();
                values.put("uhrzeit", stunden + ":" + minuten);

                if(cbKalendereintrag.isChecked())
                {
                    ContentResolver resolver = getContentResolver();
                    String[] projection = { CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME };
                    Cursor c = resolver.query(CalendarContract.Calendars.CONTENT_URI, projection, null, null, null);

                    long calenderId;
                    if (c.moveToNext()) {
                        calenderId = c.getLong(0);

                        Calendar beginTime = Calendar.getInstance();
                        beginTime.set(jahr, monat, tag, stunden, minuten);
                        long startMillis = beginTime.getTimeInMillis();
                        Calendar endTime = Calendar.getInstance();
                        endTime.set(jahr, monat, tag, stunden+1, minuten);
                        long endMillis = endTime.getTimeInMillis();

                        ContentValues kalenderVal = new ContentValues();
                        kalenderVal.put(CalendarContract.Events.CALENDAR_ID, calenderId);
                        kalenderVal.put(CalendarContract.Events.DTSTART, startMillis);
                        kalenderVal.put(CalendarContract.Events.DTEND, endMillis);
                        kalenderVal.put(CalendarContract.Events.EVENT_TIMEZONE, "CET");
                        kalenderVal.put(CalendarContract.Events.TITLE, spWorkout.getSelectedItem().toString());

                        Uri eventUri = resolver.insert(CalendarContract.Events.CONTENT_URI, kalenderVal);
                        long id = Long.parseLong(eventUri.getLastPathSegment());
                        values.put("kalendereintrag", id);
                    } else {
                        throw new RuntimeException("Kein Kalender verfuegbar");
                    }

                }

                db.insert("todo_workouthead", null, values);
                TodoWorkoutDetail.this.finish();
                Toast.makeText(getApplicationContext(), R.string.saveChanges, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        cursor.close();
        db.close();
    }
}
