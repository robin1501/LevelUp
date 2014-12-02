package com.example.krro.levelup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by krro on 18.11.2014.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String TABLE_UEBUNG = "CREATE TABLE IF NOT EXISTS uebungen ("
            + "u_id integer primary key, "
            + "beschreibung text not null, "
            + "bild text not null, "
            + "info text);";

    private static final String TABLE_WORKOUT_HEAD = "CREATE TABLE IF NOT EXISTS workouthead ("
            + "wh_id integer primary key, "
            + "beschreibung text not null, "
            + "datum date not null);";

    private static final String TABLE_WORKOUT_POS = "CREATE TABLE IF NOT EXISTS workoutpos ("
            + "wh_id integer, "
            + "wp_id integer, "
            + "u_id integer not null, "
            + "gewicht integer not null, "
            + "wiederholungen integer not null, "
            + "saetze integer not null, "
            + "PRIMARY KEY(wh_id, wp_id));";

    private static final String TABLE_PROFIL = "CREATE TABLE IF NOT EXISTS profil ("
            + "p_id integer primary key, "
            + "name text, "
            + "age integer, "
            + "gewicht integer, "
            + "groesse integer, "
            + "geschlecht text, "
            + "wunschgewicht integer);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(TABLE_UEBUNG);
        database.execSQL(TABLE_WORKOUT_HEAD);
        database.execSQL(TABLE_WORKOUT_POS);
        database.execSQL(TABLE_PROFIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE profil");
        db.execSQL("DROP TABLE uebungen");
        db.execSQL("DROP TABLE workouthead");
        db.execSQL("DROP TABLE workoutpos");

        db.execSQL(TABLE_UEBUNG);
        db.execSQL(TABLE_WORKOUT_HEAD);
        db.execSQL(TABLE_WORKOUT_POS);
        db.execSQL(TABLE_PROFIL);
    }

}
