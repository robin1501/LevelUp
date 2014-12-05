package com.example.krro.levelup;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grafik);

        OpenChart();

    }
    private GraphicalView mChart;


    private String[] mMonth = new String[] {
            "Jan", "Feb" , "Mar", "Apr", "May", "Jun", "Jul", "Aug" };


    private void OpenChart()
    {
     // Define the number of elements you want in the chart.
        int z[]={0,1,2,3,4,5,6,7};


        int x[]={10,18,32,21,48,60,53,80};


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
        Xrenderer.setColor(Color.BLUE);
        Xrenderer.setPointStyle(PointStyle.POINT);
        Xrenderer.setDisplayChartValues(true);
        Xrenderer.setLineWidth(4);
        Xrenderer.setFillPoints(true);

        // Create XYMultipleSeriesRenderer to customize the whole chart

        XYMultipleSeriesRenderer mRenderer=new XYMultipleSeriesRenderer();

        mRenderer.setChartTitle("Gewichtssteigerung");
        mRenderer.setXTitle("Datum");
        mRenderer.setYTitle("Gewicht in Kg");
        mRenderer.setZoomButtonsVisible(false);
        mRenderer.setXLabels(0);
        mRenderer.setPanEnabled(false);

        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setBackgroundColor(Color.TRANSPARENT);
        mRenderer.setMarginsColor(Color.TRANSPARENT);
        mRenderer.setShowGrid(true);

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

        //  Adding click event to the Line Chart.

        mChart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                SeriesSelection series_selection=mChart.getCurrentSeriesAndPoint();

                if(series_selection!=null)
                {
                    int series_index=series_selection.getSeriesIndex();

                    String select_series="X Series";
                    if(series_index==0)
                    {
                        select_series="X Series";
                    }else
                    {
                        select_series="Y Series";
                    }

                    String month=mMonth[(int)series_selection.getXValue()];

                    int amount=(int)series_selection.getValue();

                    Toast.makeText(getBaseContext(), select_series + "in" + month + ":" + amount, Toast.LENGTH_LONG).show();
                }
            }
        });

// Add the graphical view mChart object into the Linear layout .
        chart_container.addView(mChart);


    }

}




