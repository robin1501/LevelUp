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
    private static final int DATABASE_VERSION = 9;

    // Database creation sql statement
    private static final String TABLE_UEBUNG = "CREATE TABLE IF NOT EXISTS uebungen ("
            + "u_id integer primary key, "
            + "beschreibung text not null, "
            + "bild blob, "
            + "bauch integer default 0 CHECK (bauch IN (0,1)), "
            + "bizeps integer default 0 CHECK (bizeps IN (0,1)), "
            + "trizeps integer default 0 CHECK (trizeps IN (0,1)), "
            + "brust integer default 0 CHECK (brust IN (0,1)), "
            + "schulter integer default 0 CHECK (schulter IN (0,1)), "
            + "ruecken integer default 0 CHECK (ruecken IN (0,1)), "
            + "beine integer default 0 CHECK (beine IN (0,1)), "
            + "info text);";

    private static final String INSERT_UEBUNG = "INSERT INTO uebungen "
            + "(beschreibung, bauch, bizeps, trizeps, brust, schulter, ruecken, beine, info) VALUES "
            + "('Bankdrücken', 0, 1, 0, 1, 0, 0, 0, ''), "
            + "('Kreuzheben', 1, 0, 0, 0, 1, 1, 1, ''), "
            + "('Dips', 0, 0, 1, 1, 1, 0, 0, ''), "
            + "('Latziehen', 0, 1, 0, 0, 0, 0, 0, ''), "
            + "('Sit Ups', 1, 0, 0, 0, 0, 0, 0, 'Beine anwinkeln'), "
            + "('Beinpresse', 0, 0, 0, 0, 0, 0, 1, 'Winkel auf 40 Grad stellen'), "
            + "('Kniebeugen', 1, 0, 0, 0, 0, 1, 1, ''), "
            + "('Pull Ups', 0, 1, 0, 1, 0, 1, 0, '');";

    private static final String TABLE_WORKOUT_HEAD = "CREATE TABLE IF NOT EXISTS workouthead ("
            + "w_id integer primary key, "
            + "beschreibung text not null);";

    private static final String TABLE_WORKOUT_POS = "CREATE TABLE IF NOT EXISTS workoutpos ("
            + "w_id integer, "
            + "u_id integer not null, "
            + "gewicht integer not null, "
            + "wiederholungen integer not null, "
            + "saetze integer not null, "
            + "PRIMARY KEY(w_id, u_id));";

    private static final String TABLE_TODO_WORKOUT = "CREATE TABLE IF NOT EXISTS todo_workout ("
            + "th_id integer primary key, "
            + "w_id integer"
            + "datum date not null"
            + "erledigt integer default 0);";

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
        database.execSQL(INSERT_UEBUNG);
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
        db.execSQL(INSERT_UEBUNG);
        db.execSQL(TABLE_WORKOUT_HEAD);
        db.execSQL(TABLE_WORKOUT_POS);
        db.execSQL(TABLE_PROFIL);
    }

}
