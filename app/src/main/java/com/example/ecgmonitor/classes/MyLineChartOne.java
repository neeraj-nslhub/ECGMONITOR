package com.example.ecgmonitor.classes;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.example.ecgmonitor.LandingActivity;
import com.example.ecgmonitor.R;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MyLineChartOne {

    Context mContext;
    CombinedChart mLineChart;
    LineDataSet lineDataSet;

    public MyLineChartOne(Context context, CombinedChart lineChart) {

        mContext = context;
        mLineChart = lineChart;

        // enable description text
        mLineChart.setDrawOrder(new CombinedChart.DrawOrder[]{
                CombinedChart.DrawOrder.LINE,CombinedChart.DrawOrder.BAR});
        mLineChart.getDescription().setEnabled(true);
        mLineChart.getDescription().setText("Lead 1");
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

        CombinedData data = new CombinedData();
        LineData lineData = new LineData();


        data.setValueTextColor(R.color.red);
        data.setData(generateBarData());
        lineDataSet = createSet();//new LineDataSet(dataValue2(),"");
        lineData.addDataSet(lineDataSet);
        data.setData(lineData);
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
        xl.setAxisMaximum(600);

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
        mLineChart.getLegend().setEnabled(false);

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
                        float column = Float.parseFloat(row[0]);
                        addCSVEntry(column);
                        Thread.sleep(50);
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



    private void addCSVEntry(float event) {

        LineData data = mLineChart.getLineData();

        if (data != null) {


            ILineDataSet set = data.getDataSetByIndex(0);


            if (set == null) {
                set = createSet();
                data.addDataSet(set);
                Log.d("myline", "myLine1"+event);
            }

            data.addEntry(new Entry(data.getEntryCount(), event), 0);
            Log.d("myline", "myLine2"+event);
           // mLineChart.getLineData().addEntry(new Entry(set.getEntryCount(), event), 0);
            data.notifyDataChanged();
            mLineChart.notifyDataSetChanged();
            mLineChart.setVisibleXRange(1000, 300);
            mLineChart.moveViewToX(data.getEntryCount());
            Log.d("myline", "myLine2entrycount"+data.getEntryCount());


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

    private BarData generateBarData() {

        ArrayList<BarEntry> entries1 = new ArrayList<>();


        for (int index = 0; index < 500; index++) {
            entries1.add(new BarEntry(10, index));

        }

        BarDataSet set1 = new BarDataSet(entries1, "Lead 1");
        set1.setColor(Color.BLUE);
        set1.setValueTextColor(Color.rgb(60, 220, 78));
        set1.setValueTextSize(10f);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);



//        float groupSpace = 0.06f;
//        float barSpace = 0.02f; // x2 dataset
        float barWidth = 10f; // x2 dataset
//        // (0.45 + 0.02) * 2 + 0.06 = 1.00 -> interval per "group"
//
        BarData d = new BarData(set1);
        d.setBarWidth(barWidth);

//
//        // make this BarData object grouped
//        d.groupBars(0, groupSpace, barSpace); // start at x = 0


        return d;
    }
}
