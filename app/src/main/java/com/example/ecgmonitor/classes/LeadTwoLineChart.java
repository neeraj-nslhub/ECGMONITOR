package com.example.ecgmonitor.classes;

import android.content.Context;
import android.graphics.Color;

import com.example.ecgmonitor.R;
import com.example.ecgmonitor.model.BluetoothData;
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
import java.io.InputStream;
import java.io.InputStreamReader;

public class LeadTwoLineChart extends Thread {

    private BluetoothData mData;
    private InputStream mInputStream;
    private LineChart mLineChart;
    private Context mContext;

    public LeadTwoLineChart(LineChart lineChart, Context context, BluetoothData bluetoothData) {
        mLineChart = lineChart;
        mContext = context;
        mData = bluetoothData;

        // enable description text
        mLineChart.getDescription().setEnabled(true);
        mLineChart.getDescription().setText("Lead 2");
        mLineChart.setMinOffset(0);
        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        // set an alternative background color
        mLineChart.setBackgroundColor(Color.parseColor("#FFFFFF"));


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
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setTextColor(R.color.red);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);
        mLineChart.getAxisLeft().setDrawGridLines(true);
        mLineChart.getXAxis().setDrawGridLines(true);
        mLineChart.setDrawBorders(true);
        mLineChart.getXAxis().setDrawLabels(false);
        mLineChart.getAxisLeft().setDrawLabels(false);
        mLineChart.setExtraOffsets(0, 0, 0, -15);
    }

    private void addCSVEntry(float event) {

        LineData data = mLineChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addEntry(new Entry(set.getEntryCount(), event + 5), 0);
            data.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.setVisibleXRange(500, 500);
            mLineChart.moveViewToX(data.getEntryCount());

        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "");
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

    public void run() {

        mInputStream = mData.get1();
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
        try {
            reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] row = line.split(",");
            int column0 = Integer.parseInt(row[1]);
            addCSVEntry(column0);


        }

    }
}