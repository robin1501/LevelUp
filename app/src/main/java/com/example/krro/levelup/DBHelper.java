package com.example.krro.levelup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by krro on 18.11.2014.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String TABLE_UEBUNG = "create table uebungen ("
            + "u_id integer primary key autoincrement, "
            + "beschreibung text not null"
            + "bild text not null, "
            + "zeit int not null, "
            + "info text);";

    private static final String TABLE_WORKOUT_HEAD = "create table workout ("
            + "wh_id integer primary key autoincrement, "
            + "beschreibung text not null, "
            + "datum date not null);";

    private static final String TABLE_WORKOUT_POS = "create table workout ("
            + "wh_id integer primary key, "
            + "wp_id integer primary key autoincrement, "
            + "u_id integer not null, "
            + "gewicht integer not null, "
            + "wiederholungen integer not null, "
            + "saetze integer not null);";

    private static final String TABLE_PROFIL = "create table profil ("
            + "p_id integer primary key autoincrement, "
            + "name text, "
            + "gewicht integer, "
            + "groesse integer, "
            + "geschlecht char(1), "
            + "wunschgewicht int);";

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

    }

}
