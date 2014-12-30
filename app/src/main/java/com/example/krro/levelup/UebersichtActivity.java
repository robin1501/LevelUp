package com.example.krro.levelup;

import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;



public class UebersichtActivity extends Activity {

    public static boolean isBetween(double x, double lower, double upper) {
        return lower <= x && x <= upper;
    }

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    private static int[] COLORS = new int[] { Color.BLUE, Color.RED,};
    private static double[] VALUES = new double[2];
    private static String[] NAME_LIST = new String[] { "Erledigt", "Offen", };
    private CategorySeries mSeries = new CategorySeries("");
    private DefaultRenderer mRenderer = new DefaultRenderer();
    private GraphicalView mChartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.uebersicht);

        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getWritableDatabase();

        String query = "SELECT COUNT(*) FROM todo_workouthead "
                + "WHERE STRFTIME('%W', SUBSTR(datum,7,4)||'-'||SUBSTR(datum,4,2)||'-'||SUBSTR(datum,1,2)) = STRFTIME('%W', 'now') "
                + "AND SUBSTR(datum,7,4) = STRFTIME('%Y', 'now') "
                + "AND abgeschlossen = 1";
        Cursor cursor = db.rawQuery(query, null);
        int erledigt = 0;
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            erledigt = cursor.getInt(0);
        }

        query = "SELECT workouts FROM profil";
        cursor = db.rawQuery(query, null);
        int workoutsGesamt = 0;
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            workoutsGesamt = cursor.getInt(0);
        }

        TextView tvWorkouts = (TextView)findViewById(R.id.uProzent);
        tvWorkouts.setText(erledigt + " von " + workoutsGesamt + " Workouts");

        VALUES[0] = Math.round(100.0 / workoutsGesamt * erledigt * 10) / 10.0;
        VALUES[1] = 100.0 - VALUES[0];

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setChartTitleTextSize(20);
        mRenderer.setLabelsTextSize(20);
        mRenderer.setLegendTextSize(20);
        mRenderer.setPanEnabled(false);
        mRenderer.setLegendHeight(-20);
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setStartAngle(90);

        for (int i = 0; i < VALUES.length; i++) {
            mSeries.add(NAME_LIST[i] + " " + VALUES[i] + "%", VALUES[i]);
            SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();
            renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);
            mRenderer.addSeriesRenderer(renderer);
        }

        if (mChartView != null) {
            mChartView.repaint();
        }

        query = "SELECT datum FROM todo_workouthead WHERE abgeschlossen = 1 "
                + "ORDER BY SUBSTR(datum, 7, 4) DESC, SUBSTR(datum, 4, 2) DESC, SUBSTR(datum, 1, 2) DESC LIMIT 1";
        cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            TextView tvLetztesWorkout = (TextView) findViewById(R.id.uLetWorDat);
            tvLetztesWorkout.setText(cursor.getString(0));
        }

        query = "SELECT datum FROM todo_workouthead WHERE abgeschlossen = 0 "
                + "ORDER BY SUBSTR(datum, 7, 4), SUBSTR(datum, 4, 2), SUBSTR(datum, 1, 2) LIMIT 1";
        cursor = db.rawQuery(query, null);
        if(cursor.getCount() != 0) {
            cursor.moveToFirst();
            TextView tvNaechstesWorkout = (TextView) findViewById(R.id.uNaeWorDat);
            tvNaechstesWorkout.setText(cursor.getString(0));
        }

        query = "SELECT gewicht, groesse, age  from profil";
        cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            double gewicht = cursor.getInt(0);
            double groesse = cursor.getInt(1) / 100.0;
            double bmi = Math.round(gewicht / (groesse * groesse) * 100) / 100.0;

            TextView txtBmi = (TextView) findViewById(R.id.uBmiZahl);
            txtBmi.setText(bmi + "");

            int alter = cursor.getInt(2);
            int minBMI = 0;
            int maxBMI = 0;

            if (isBetween(alter, 19, 24)) {
                minBMI = 19;
                maxBMI = 24;
            } else if (isBetween(alter, 25, 34)) {
                minBMI = 20;
                maxBMI = 25;
            } else if (isBetween(alter, 35, 44)) {
                minBMI = 21;
                maxBMI = 26;
            }else if (isBetween(alter, 45, 54)) {
                minBMI = 22;
                maxBMI = 27;
            }else if (isBetween(alter, 55, 64)) {
                minBMI = 23;
                maxBMI = 28;
            }else if (alter > 64) {
                minBMI = 24;
                maxBMI = 29;
            }

            if (isBetween(bmi, minBMI, maxBMI)) {
                txtBmi.setTextColor(Color.GREEN);
            } else {
                txtBmi.setTextColor(Color.RED);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mChartView == null) {
            LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
            mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);
            mRenderer.setSelectableBuffer(10);

            layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        }
        else {
            mChartView.repaint();
        }


    }


}
