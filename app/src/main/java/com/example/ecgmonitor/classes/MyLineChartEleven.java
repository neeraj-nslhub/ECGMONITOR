package com.example.ecgmonitor.classes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.ecgmonitor.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyLineChartEleven {

    Context mContext;
    LineChart mLineChart;

    public MyLineChartEleven(Context context, LineChart lineChart) {

        mContext = context;
        mLineChart = lineChart;

        // enable description text
        mLineChart.getDescription().setEnabled(true);
        mLineChart.getDescription().setText("Lead 11");
        mLineChart.setMinOffset(0);
        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        // set an alternative background color
        //  mLineChart.setBackgroundColor(Color.parseColor("#FFFFFF"));


        LineData data = new LineData();
        data.setValueTextColor(R.color.red);

        // add empty data
        mLineChart.setData(data);

        // get the legend (only possible after setting data)
        Legend l = mLineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(R.color.red);

        XAxis xl = mLineChart.getXAxis();
        xl.setTextColor(R.color.red);
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setEnabled(true);
        leftAxis.setTextColor(R.color.red);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(5f);
        leftAxis.setAxisMinimum(-5f);
        leftAxis.setDrawGridLines(false);


        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);
        rightAxis.setTextColor(R.color.red);
        rightAxis.setAxisMaximum(5f);
        rightAxis.setAxisMinimum(-5f);
        rightAxis.setDrawGridLines(false);

        mLineChart.getAxisLeft().setDrawGridLines(false);
        mLineChart.getXAxis().setDrawGridLines(false);
        mLineChart.setDrawBorders(true);
        mLineChart.getXAxis().setDrawLabels(false);
        mLineChart.getAxisLeft().setDrawLabels(false);
        mLineChart.setExtraOffsets(0, 0, 0, -15);
        mLineChart.setDrawBorders(false);

        Thread thread = new Thread() {
            @Override
            public void run() {
                InputStreamReader is = null;
                try {
                    is = new InputStreamReader(mContext.getAssets().open("ecg_sample.csv"));
                    BufferedReader reader = new BufferedReader(is);
                    reader.readLine();

                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] row = line.split(",");
                        float column = Float.parseFloat(row[10]);
                          addCSVEntry(column);
                        Thread.sleep(100);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        };

        thread.start();
    }

    private void addCSVEntry(float event) {

        LineData data = mLineChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), event), 0);
            data.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.setVisibleXRange(500, 300);
            mLineChart.moveViewToX(data.getEntryCount());

        }
    }
    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null,"");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(1f);
        set.setFormLineWidth(0f);
        set.setFormSize(0f);
        set.setColor(mContext.getColor(R.color.red));
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }

}
