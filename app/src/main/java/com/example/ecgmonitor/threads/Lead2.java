package com.example.ecgmonitor.threads;

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

public class Lead2 extends Thread{
    private BluetoothData mData;
    private InputStream mInputStream;
    private LineChart mLineChart;
    private Context mContext;
    private volatile boolean isStopped = false;
    public Lead2(LineChart lineChart, Context context, BluetoothData bluetoothData) {
        mLineChart = lineChart;
        mContext = context;
        mData = bluetoothData;

        // enable description text
        mLineChart.getDescription().setEnabled(true);
        mLineChart.getDescription().setText("lead II");
        mLineChart.setMinOffset(0);
        // enable touch gestures
        mLineChart.setTouchEnabled(true);

        // enable scaling and dragging
        mLineChart.setDragEnabled(true);
        mLineChart.setScaleEnabled(true);
        mLineChart.setDrawGridBackground(false);
        mLineChart.getLegend().setEnabled(false);

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart.setPinchZoom(true);

        LineData data = new LineData();
        // add empty data
        mLineChart.setData(data);


        XAxis xl = mLineChart.getXAxis();
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);

        mLineChart.setDrawBorders(false);
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
            data.addEntry(new Entry(set.getEntryCount(), event+5), 0);
            data.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.setVisibleXRange(125, 125);
            mLineChart.moveViewToX(data.getEntryCount());

        }
    }
    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null,"");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(1f);
        set.setFormLineWidth(0f);
        set.setFormSize(0f);
        set.setColor(mContext.getColor(R.color.black));
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

        while (!isStopped) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String[] row = line.split(",");
            if(row.length>1 && isNumeric(row[1])) {
                float column1 = Float.parseFloat(row[1]);
                addCSVEntry(column1);
            }


        }

    }
    public void setStopped(){
        isStopped = true;
    }
    public static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}
