package com.example.krro.levelup;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.SeriesSelection;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

/**
 * Created by Patrick W on 04.12.2014.
 */



public class Grafik extends Activity {
    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private int[] gewicht;
    private String[] datum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik);

        int id;
        Intent uebungDetail = getIntent();
        id = uebungDetail.getIntExtra("id", 0);
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();
        String query = "SELECT tp.gewicht, th.datum FROM todo_workoutpos tp inner join todo_workouthead th on tp.t_id = th.t_id WHERE u_id = " + id;

        Cursor cursor = db.rawQuery(query, null);

        int gewicht[]=new int[cursor.getCount()];

        String datum[]=new String[cursor.getCount()];

        int i=0;

        cursor.moveToFirst();
        while (cursor.isAfterLast() == false)
        {
            gewicht[i]  = cursor.getInt(0);
            datum[i] = cursor.getString(1);
            i++;
            cursor.moveToNext();
        }

       // OpenChart();

         GraphicalView mChart;

         String[] mMonth = datum;

        int[] z= new int[datum.length];

        // Define the number of elements you want in the chart.
        for(int a=0;a<datum.length;a++)
        {
            z[a]=a;

        }
        //

            int x[]= gewicht;


            // Create XY Series for X Series.
            XYSeries xSeries=new XYSeries("Gewichtsteigerung");


            //  Adding data to the X Series.
            for(int d=0;d<z.length;d++)
            {
                xSeries.add(z[d],x[d]);

            }

            // Create a Dataset to hold the XSeries.

            XYMultipleSeriesDataset dataset=new XYMultipleSeriesDataset();

            // Add X series to the Dataset.
            dataset.addSeries(xSeries);


            // Create XYSeriesRenderer to customize XSeries

            XYSeriesRenderer Xrenderer=new XYSeriesRenderer();
            Xrenderer.setColor(Color.BLACK);
            Xrenderer.setPointStyle(PointStyle.POINT);
            Xrenderer.setDisplayChartValues(true);
            Xrenderer.setLineWidth(4);
            Xrenderer.setFillPoints(true);

            // Create XYMultipleSeriesRenderer to customize the whole chart

            XYMultipleSeriesRenderer mRenderer=new XYMultipleSeriesRenderer();

            mRenderer.setChartTitle("Gewichtssteigerung");
            mRenderer.setXTitle("Datum");
            mRenderer.setYTitle("Gewicht in Kg");
            mRenderer.setZoomEnabled(false);
            mRenderer.setZoomButtonsVisible(false);
            mRenderer.setXLabels(0);
            mRenderer.setPanEnabled(true);

            mRenderer.setApplyBackgroundColor(true);
            mRenderer.setBackgroundColor(Color.TRANSPARENT);
            mRenderer.setMarginsColor(Color.TRANSPARENT);

            mRenderer.setShowGrid(false);

            mRenderer.setClickEnabled(true);

            for(int t=0;t<z.length;t++)
            {
                mRenderer.addXTextLabel(t, mMonth[t]);
            }

            // Adding the XSeriesRenderer to the MultipleRenderer.
            mRenderer.addSeriesRenderer(Xrenderer);


            LinearLayout chart_container=(LinearLayout)findViewById(R.id.Chart_layout);

            // Creating an intent to plot line chart using dataset and multipleRenderer

            mChart=(GraphicalView) ChartFactory.getLineChartView(getBaseContext(), dataset, mRenderer);


// Add the graphical view mChart object into the Linear layout .
            chart_container.addView(mChart);


        }

    }







