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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik);

        OpenChart();

        int id;
        Intent uebungDetail = getIntent();
        id = uebungDetail.getIntExtra("id", 0);
        dbHelper = new DBHelper(getApplicationContext());
        db = dbHelper.getReadableDatabase();

     //   String query = "SELECT gewicht FROM todo_workoutpos WHERE u_id = " + 0;
        String query = "SELECT tp.gewicht, th.datum FROM todo_workoutpos tp inner join todo_workouthead th on tp.t_id = th.t_id WHERE u_id = " + id;

        Cursor cursor = db.rawQuery(query, null);
        Log.i("Number of Records"," :: "+cursor.getCount());

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();

            double gewicht = cursor.getDouble(0);
            Log.i("Number of Records 1"," :: "+cursor.getCount());
        }
    }
    private GraphicalView mChart;


    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Okt",
    "Nov", "Dez"};

    private void OpenChart()
    {
     // Define the number of elements you want in the chart.
        int z[]={0,1,2,3,4,5,6,7};



        int x[]={10,18,32,21,48,60,53,60};


        // Create XY Series for X Series.
        XYSeries xSeries=new XYSeries("Gewichtsteigerung");


        //  Adding data to the X Series.
        for(int i=0;i<z.length;i++)
        {
            xSeries.add(z[i],x[i]);

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

        for(int i=0;i<z.length;i++)
        {
            mRenderer.addXTextLabel(i, mMonth[i]);
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




