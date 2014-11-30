package com.example.krro.levelup;

import java.util.GregorianCalendar;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
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
    }
}


