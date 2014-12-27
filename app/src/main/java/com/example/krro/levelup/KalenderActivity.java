package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class KalenderActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender);

    }

    public void onClick(View view) {

        //Kalender Id selektieren
        ContentResolver resolver = getContentResolver();
        String[] projection = { CalendarContract.Calendars._ID, CalendarContract.Calendars.CALENDAR_DISPLAY_NAME };
        Cursor cursor = resolver.query(CalendarContract.Calendars.CONTENT_URI, projection, null, null, null);

        long calenderId;
        if (cursor.moveToNext()) {
            calenderId = cursor.getLong(0);
            String name = cursor.getString(1);
            Log.i("My", "Kalender ID " + calenderId + ": " + name);
        } else {
            throw new RuntimeException("Kein Kalender verfuegbar");
        }
        // Construct event details
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, Calendar.JANUARY, 14, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, Calendar.JANUARY, 14, 8, 45);
        endMillis = endTime.getTimeInMillis();

/*
        //Termin hizufügen
        long now = System.currentTimeMillis();
        long startMillis = now + 60 * 60 * 1000;
        long endMillis = startMillis + 60 * 60 * 1000;
*/
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Events.CALENDAR_ID, calenderId);
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, "CET");
        values.put(CalendarContract.Events.TITLE, "App entwickeln");
        values.put(CalendarContract.Events.DESCRIPTION, "Mit Android 4.0 APIs");
        Uri eventUri = resolver.insert(CalendarContract.Events.CONTENT_URI, values);
        long id = Long.parseLong(eventUri.getLastPathSegment());
        Log.i("My", "Neues Event mit ID " + id + " hinzugefuegt");

        /*
        //Termin ausgeben
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
        ContentUris.appendId(builder, now);
        ContentUris.appendId(builder, now + 2014 * 24 * 60 * 60 * 1000);

        projection = new String[] { CalendarContract.Instances.EVENT_ID, CalendarContract.Instances.BEGIN, CalendarContract.Instances.TITLE };
        cursor = resolver.query(builder.build(), projection, null, null, null);
        while (cursor.moveToNext()) {
            long eventId = cursor.getLong(0);
            Date date = new Date(cursor.getLong(1));
            String title = cursor.getString(2);
            Log.i("My", "Event ID " + eventId + ": " + title + " (" + date + ")");
        }
*/
        /*

        // Teil 4: Angelegten Termin wieder loeschen
         // ----------------------------------
        int deleteCount = getContentResolver().delete(eventUri, null, null);
        Log.i("My", "Termine geloescht: " + deleteCount);
        */

    }

}