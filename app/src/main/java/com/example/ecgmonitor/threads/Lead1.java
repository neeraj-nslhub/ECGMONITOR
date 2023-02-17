package com.example.ecgmonitor.threads;

import android.content.Context;
import android.graphics.Color;

import com.example.ecgmonitor.R;
import com.example.ecgmonitor.model.BluetoothData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
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
import java.util.ArrayList;

public class Lead1 extends Thread{

    private BluetoothData mData;
    private InputStream mInputStream;
    private LineChart mLineChart;
    private Context mContext;
    private volatile boolean isStopped = false;
    ArrayList<ILineDataSet> mILineDataset;
    public Lead1(LineChart lineChart, Context context, BluetoothData bluetoothData) {
        mLineChart = lineChart;
        mContext = context;
        mData = bluetoothData;

        // enable description text
        mLineChart.getDescription().setEnabled(true);
        mLineChart.getDescription().setText("lead I");
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

//        LineDataSet lineDataSet = new LineDataSet(dataValue2(),"");
//        LineDataSet lineDataSet1 = createSet();
        LineData data = new LineData();
        // add empty data
     //   data.addDataSet(lineDataSet);
     //   data.addDataSet(lineDataSet1);
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
        mLineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
      //  mLineChart.getXAxis().setLabelCount(5);





    }

    private void addCSVEntry(float event) {

//        LineDataSet lineDataSet = new LineDataSet(dataValue2(), "");
//        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(lineDataSet);
//        lineDataSet.setLineWidth(1f);

        LineData data = mLineChart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            ILineDataSet set1 = data.getDataSetByIndex(1);

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            if (set1 == null) {
                set1 = createSet1();
                data.addDataSet(set1);
                data.addEntry(new Entry(set1.getEntryCount(), 5), 1);
                data.addEntry(new Entry(set1.getEntryCount(), 6), 1);
                data.addEntry(new Entry(set1.getEntryCount(), 7), 1);
                data.addEntry(new Entry(set1.getEntryCount(), 8), 1);
                data.addEntry(new Entry(set1.getEntryCount(), 9), 1);


            }
            data.addEntry(new Entry(set1.getEntryCount(), event+8), 1);
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

    private LineDataSet createSet1() {
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
    public void run() {
        mInputStream = mData.get();
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
            if(row.length>0 && isNumeric(row[0])) {
                float column0 = Float.parseFloat(row[0]);
                addCSVEntry(column0);
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

    private ArrayList<Entry> dataValue2()
    {
        ArrayList<Entry> dataVal2 = new ArrayList<Entry>();
        dataVal2.add(new Entry(5,5));
        dataVal2.add(new Entry(5,10));
        dataVal2.add(new Entry(5,0.10f));
        dataVal2.add(new Entry(5,10));
        dataVal2.add(new Entry(5,0.9f));
        dataVal2.add(new Entry(5,10));
        dataVal2.add(new Entry(5,0.7f));
        dataVal2.add(new Entry(1000,10));
        dataVal2.add(new Entry(50,0.5f));
        dataVal2.add(new Entry(5,0.4f));
        dataVal2.add(new Entry(10,10));
        dataVal2.add(new Entry(10,10));
        dataVal2.add(new Entry(10,0.1f));
        return dataVal2;
    }
}
