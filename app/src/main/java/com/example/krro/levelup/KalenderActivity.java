package com.example.krro.levelup;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;

import java.util.Calendar;
import java.util.TimeZone;

public class KalenderActivity extends Activity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalender);

    }

    public void onClick(View view) {

        Uri uri = CalendarContract.Calendars.CONTENT_URI;
        String[] projection = new String[] {
                CalendarContract.Calendars._ID,
                CalendarContract.Calendars.ACCOUNT_NAME,
                CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
                CalendarContract.Calendars.NAME,
                CalendarContract.Calendars.CALENDAR_COLOR
        };
        Cursor calendarCursor = managedQuery(uri, projection, null, null, null);

        // Construct event details
        long startMillis = 0;
        long endMillis = 0;
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2015, Calendar.JANUARY, 14, 7, 30);
        startMillis = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2015, Calendar.JANUARY, 14, 8, 45);
        endMillis = endTime.getTimeInMillis();

        // Insert Event
        ContentResolver cr = getContentResolver();
        ContentValues values = new ContentValues();
        TimeZone timeZone = TimeZone.getDefault();
        values.put(CalendarContract.Events.DTSTART, startMillis);
        values.put(CalendarContract.Events.DTEND, endMillis);
        values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
        values.put(CalendarContract.Events.TITLE, "Walk The Dog");
        values.put(CalendarContract.Events.DESCRIPTION, "My dog is bored, so we're going on a really long walk!");
        values.put(CalendarContract.Events.CALENDAR_ID, 3);
        Uri uri2 = cr.insert(uri, values);


        // Retrieve ID for new event
        String eventID = uri2.getLastPathSegment();




    }

}