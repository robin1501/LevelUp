package com.example.krro.levelup;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "fitness.db";
    private static final int DATABASE_VERSION = 20;

    // Database creation sql statement
    private static final String TABLE_UEBUNG = "CREATE TABLE IF NOT EXISTS uebungen ("
            + "u_id integer primary key, "
            + "beschreibung text not null, "
            + "bild blob, "
            + "gewicht integer default 0, "
            + "wiederholungen integer default 0, "
            + "saetze integer default 0, "
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
            + "('Pull-Over', 0, 0, 0, 1, 0, 1, 0, ''), "
            + "('Liegestütze', 0, 0, 1, 1, 0, 0, 0, ''), "
            + "('Kreuzheben', 1, 0, 0, 0, 1, 1, 1, ''), "
            + "('Rudern am Kabelzug', 0, 1, 0, 0, 0, 1, 0, ''), "
            + "('Hüftheben', 1, 0, 0, 0, 0, 0, 0, ''), "
            + "('Dips', 0, 0, 1, 1, 1, 0, 0, ''), "
            + "('Latziehen', 0, 1, 0, 0, 0, 0, 0, ''), "
            + "('Sit Ups', 1, 0, 0, 0, 0, 0, 0, 'Beine anwinkeln'), "
            + "('Beinpresse', 0, 0, 0, 0, 0, 0, 1, 'Winkel auf 40 Grad stellen'), "
            + "('Kniebeugen', 1, 0, 0, 0, 0, 1, 1, ''), "
            + "('Rückenstrecker', 0, 0, 0, 0, 0, 1, 1, ''), "
            + "('Kurzhantel-Ausfallschritt', 0, 0, 0, 0, 0, 0, 1, ''), "
            + "('Pull Ups', 0, 1, 0, 1, 0, 1, 0, '');";

    private static final String TABLE_WORKOUT_HEAD = "CREATE TABLE IF NOT EXISTS workouthead ("
            + "w_id integer primary key, "
            + "beschreibung text not null);";

    private static final String INSERT_WORKOUT_HEAD = "INSERT INTO workouthead (beschreibung) VALUES "
            + "('Bauch'),"
            + "('Rücken'),"
            + "('Beine'),"
            + "('Arme'),"
            + "('Brust');";

    private static final String TABLE_WORKOUT_POS = "CREATE TABLE IF NOT EXISTS workoutpos ("
            + "w_id integer, "
            + "u_id integer, "
            + "PRIMARY KEY(w_id, u_id));";

    private static final String INSERT_WORKOUT_POS = "INSERT INTO workoutpos (w_id, u_id) "
            + "SELECT 1, u_id FROM uebungen WHERE bauch = 1 "
            + "UNION ALL SELECT 2, u_id FROM uebungen WHERE brust = 1 OR bizeps = 1 OR trizeps = 1;";

    private static final String TABLE_TODO_WORKOUT_HEAD = "CREATE TABLE IF NOT EXISTS todo_workouthead ("
            + "t_id integer primary key, "
            + "w_id integer, "
            + "datum text not null, "
            + "uhrzeit text, "
            + "kalendereintrag integer, "
            + "abgeschlossen integer default 0 CHECK (abgeschlossen IN (0,1)));";

    private static final String TABLE_TODO_WORKOUT_POS = "CREATE TABLE IF NOT EXISTS todo_workoutpos ("
            + "t_id integer, "
            + "u_id integer, "
            + "gewicht integer not null, "
            + "wiederholungen integer not null, "
            + "saetze integer not null, "
            + "PRIMARY KEY(t_id, u_id));";

    private static final String TABLE_PROFIL = "CREATE TABLE IF NOT EXISTS profil ("
            + "p_id integer primary key, "
            + "name text, "
            + "age integer, "
            + "gewicht integer, "
            + "groesse integer, "
            + "geschlecht text, "
            + "wunschgewicht integer, "
            + "workouts integer);";

    private static final String TABLE_TAGEBUCH = "CREATE TABLE IF NOT EXISTS tagebuch ("
            + "_id integer primary key, "
            + "datum string, "
            + "bild blob, "
            + "gewicht integer, "
            + "schulterumfang integer, "
            + "armumfang integer, "
            + "brustumfang integer, "
            + "bauchumfang integer, "
            + "hueftumfang integer, "
            + "beinumfang integer);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_UEBUNG);
        db.execSQL(INSERT_UEBUNG);
        db.execSQL(TABLE_WORKOUT_HEAD);
        db.execSQL(INSERT_WORKOUT_HEAD);
        db.execSQL(TABLE_WORKOUT_POS);
        db.execSQL(INSERT_WORKOUT_POS);
        db.execSQL(TABLE_TODO_WORKOUT_HEAD);
        db.execSQL(TABLE_TODO_WORKOUT_POS);
        db.execSQL(TABLE_PROFIL);
        db.execSQL(TABLE_TAGEBUCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS uebungen");
        db.execSQL("DROP TABLE IF EXISTS workouthead");
        db.execSQL("DROP TABLE IF EXISTS workoutpos");
        db.execSQL("DROP TABLE IF EXISTS todo_workouthead");
        db.execSQL("DROP TABLE IF EXISTS todo_workoutpos");
        db.execSQL("DROP TABLE IF EXISTS profil");
        db.execSQL("DROP TABLE IF EXISTS tagebuch");

        db.execSQL(TABLE_UEBUNG);
        db.execSQL(INSERT_UEBUNG);
        db.execSQL(TABLE_WORKOUT_HEAD);
        db.execSQL(INSERT_WORKOUT_HEAD);
        db.execSQL(TABLE_WORKOUT_POS);
        db.execSQL(INSERT_WORKOUT_POS);
        db.execSQL(TABLE_TODO_WORKOUT_HEAD);
        db.execSQL(TABLE_TODO_WORKOUT_POS);
        db.execSQL(TABLE_PROFIL);
        db.execSQL(TABLE_TAGEBUCH);
    }

}
