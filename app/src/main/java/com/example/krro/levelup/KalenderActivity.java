package com.example.krro.levelup;

import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class KalenderActivity extends Activity {

    public static final String[] EVENT_PROJECTION = new String[] {
            Calendars._ID, // 0
            Calendars.ACCOUNT_NAME, // 1
            Calendars.CALENDAR_DISPLAY_NAME // 2
    };


    private static final int PROJECTION_DISPLAY_NAME_INDEX = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender);
    }


    public void onClick(View view) {

     //Kalender Id selektieren
        ContentResolver resolver = getContentResolver();
         String[] projection = { Calendars._ID, Calendars.CALENDAR_DISPLAY_NAME };
         Cursor cursor = resolver.query(Calendars.CONTENT_URI, projection, null, null, null);

        long calenderId;
        if (cursor.moveToNext()) {
                 calenderId = cursor.getLong(0);
                 String name = cursor.getString(1);
                 Log.i("My", "Kalender ID " + calenderId + ": " + name);
             } else {
                 throw new RuntimeException("Kein Kalender verfuegbar");
             }

     //Termin hizufügen
        long now = System.currentTimeMillis();
        long startMillis = now + 60 * 60 * 1000;
        long endMillis = startMillis + 60 * 60 * 1000;

         ContentValues values = new ContentValues();
         values.put(Events.CALENDAR_ID, calenderId);
         values.put(Events.DTSTART, startMillis);
         values.put(Events.DTEND, endMillis);
         values.put(Events.EVENT_TIMEZONE, "CET");
         values.put(Events.TITLE, "App entwickeln");
         values.put(Events.DESCRIPTION, "Mit Android 4.0 APIs");
         Uri eventUri = resolver.insert(Events.CONTENT_URI, values);
         long id = Long.parseLong(eventUri.getLastPathSegment());
         Log.i("My", "Neues Event mit ID " + id + " hinzugefuegt");

        //Termin ausgeben
        Uri.Builder builder = CalendarContract.Instances.CONTENT_URI.buildUpon();
         ContentUris.appendId(builder, now);
         ContentUris.appendId(builder, now + 200l * 24 * 60 * 60 * 1000);

         projection = new String[] { CalendarContract.Instances.EVENT_ID, CalendarContract.Instances.BEGIN, CalendarContract.Instances.TITLE };
         cursor = resolver.query(builder.build(), projection, null, null, null);
         while (cursor.moveToNext()) {
                long eventId = cursor.getLong(0);
                Date date = new Date(cursor.getLong(1));
                String title = cursor.getString(2);
                Log.i("My", "Event ID " + eventId + ": " + title + " (" + date + ")");
             }
/*
        // Teil 4: Angelegten Termin wieder loeschen
         // ----------------------------------
         int deleteCount = getContentResolver().delete(eventUri, null, null);
         Log.i("My", "Termine geloescht: " + deleteCount);
*/
    }
     /*
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(Events.TITLE, "Brust / Bizeps Training");
        intent.putExtra(Events.EVENT_LOCATION, "SportPrinz Freiburg");
        intent.putExtra(Events.DESCRIPTION, "Disco Pumpen");

        GregorianCalendar calDate = new GregorianCalendar(2014, 12, 12);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                calDate.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                calDate.getTimeInMillis());

        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);

        intent.putExtra(Events.RRULE,
                "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH");

        intent.putExtra(Events.ACCESS_LEVEL, Events.ACCESS_PRIVATE);
        intent.putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY);

        startActivity(intent);

    }

    public void queryCalendar(View view) {
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        String selection = "((" + Calendars.ACCOUNT_NAME + " = ?) AND ("
                + Calendars.ACCOUNT_TYPE + " = ?))";

        String[] selectionArgs = new String[] { "Lars.Vogel@gmail.com",
                "com.google" };
        cur = cr.query(uri, EVENT_PROJECTION, selection, selectionArgs, null);

        while (cur.moveToNext()) {
            String displayName = null;
            displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);
            Toast.makeText(this, "Calendar " + displayName, Toast.LENGTH_SHORT)
                    .show();
        }
    } */
}


