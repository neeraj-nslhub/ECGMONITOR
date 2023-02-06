package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.ecgmonitor.classes.InputStreamData;
import com.example.ecgmonitor.classes.LeadEightLineChart;
import com.example.ecgmonitor.classes.LeadElevenLineChart;
import com.example.ecgmonitor.classes.LeadFiveLineChart;
import com.example.ecgmonitor.classes.LeadFourLineChart;
import com.example.ecgmonitor.classes.LeadNineLineChart;
import com.example.ecgmonitor.classes.LeadOneLineChart;
import com.example.ecgmonitor.classes.LeadSevenLineChart;
import com.example.ecgmonitor.classes.LeadSixLineChart;
import com.example.ecgmonitor.classes.LeadTenLineChart;
import com.example.ecgmonitor.classes.LeadThreeLineChart;
import com.example.ecgmonitor.classes.LeadTwelveLineChart;
import com.example.ecgmonitor.classes.LeadTwoLineChart;
import com.example.ecgmonitor.classes.MyLineChartEight;
import com.example.ecgmonitor.classes.MyLineChartEleven;
import com.example.ecgmonitor.classes.MyLineChartFive;
import com.example.ecgmonitor.classes.MyLineChartFour;
import com.example.ecgmonitor.classes.MyLineChartNine;
import com.example.ecgmonitor.classes.MyLineChartOne;
import com.example.ecgmonitor.classes.MyLineChartSeven;
import com.example.ecgmonitor.classes.MyLineChartSix;
import com.example.ecgmonitor.classes.MyLineChartTen;
import com.example.ecgmonitor.classes.MyLineChartThree;
import com.example.ecgmonitor.classes.MyLineChartTwelve;
import com.example.ecgmonitor.classes.MyLineChartTwo;
import com.example.ecgmonitor.databinding.ActivityGraphBinding;
import com.example.ecgmonitor.model.BluetoothData;

import java.io.IOException;

public class GraphActivity extends AppCompatActivity  {

    ActivityGraphBinding activityGraphBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityGraphBinding = ActivityGraphBinding.inflate(getLayoutInflater());
        setContentView(activityGraphBinding.getRoot());
//
        MyLineChartOne myLineChartOne = new MyLineChartOne(GraphActivity.this, activityGraphBinding.lineChartOne);
        MyLineChartTwo myLineChartTwo = new MyLineChartTwo(GraphActivity.this, activityGraphBinding.lineChartTwo);
        MyLineChartThree myLineChartThree = new MyLineChartThree(GraphActivity.this, activityGraphBinding.lineChartThree);
        MyLineChartFour myLineChartFour = new MyLineChartFour(GraphActivity.this, activityGraphBinding.lineChartFour);
        MyLineChartFive myLineChartFive = new MyLineChartFive(GraphActivity.this, activityGraphBinding.lineChartFive);
        MyLineChartSix myLineChartSix = new MyLineChartSix(GraphActivity.this, activityGraphBinding.lineChartSix);
        MyLineChartSeven myLineChartSeven = new MyLineChartSeven(GraphActivity.this, activityGraphBinding.lineChartSeven);
        MyLineChartEight myLineChartEight = new MyLineChartEight(GraphActivity.this, activityGraphBinding.lineChartEight);
        MyLineChartNine myLineChartNine = new MyLineChartNine(GraphActivity.this, activityGraphBinding.lineChartNine);
        MyLineChartTen myLineChartTen = new MyLineChartTen(GraphActivity.this, activityGraphBinding.lineChartTen);
        MyLineChartEleven myLineChartEleven = new MyLineChartEleven(GraphActivity.this, activityGraphBinding.lineChartEleven);
        MyLineChartTwelve myLineChartTwelve = new MyLineChartTwelve(GraphActivity.this, activityGraphBinding.lineChartTwelve);


//        BluetoothData bluetoothData = new BluetoothData();
//        InputStreamData inputStreamData = null;
//        try {
//            inputStreamData = new InputStreamData(GraphActivity.this, bluetoothData);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        LeadOneLineChart leadOneLineChart = new LeadOneLineChart(activityGraphBinding.lineChartOne, GraphActivity.this, bluetoothData);
//        LeadTwoLineChart leadTwoLineChart = new LeadTwoLineChart(activityGraphBinding.lineChartTwo, GraphActivity.this, bluetoothData);
//        LeadThreeLineChart leadThreeLineChart  = new LeadThreeLineChart(activityGraphBinding.lineChartThree, GraphActivity.this, bluetoothData);
//        LeadFourLineChart leadFourLineChart = new LeadFourLineChart(activityGraphBinding.lineChartFour, GraphActivity.this, bluetoothData);
//        LeadFiveLineChart leadFiveLineChart = new LeadFiveLineChart(activityGraphBinding.lineChartFive, GraphActivity.this, bluetoothData);
//        LeadSixLineChart leadSixLineChart = new LeadSixLineChart(activityGraphBinding.lineChartSix, GraphActivity.this, bluetoothData);
//        LeadSevenLineChart leadSevenLineChart = new LeadSevenLineChart(activityGraphBinding.lineChartSeven, GraphActivity.this, bluetoothData);
//        LeadEightLineChart leadEightLineChart = new LeadEightLineChart(activityGraphBinding.lineChartEight, GraphActivity.this, bluetoothData);
//        LeadNineLineChart leadNineLineChart = new LeadNineLineChart(activityGraphBinding.lineChartNine, GraphActivity.this, bluetoothData);
//        LeadTenLineChart leadTenLineChart = new LeadTenLineChart(activityGraphBinding.lineChartTen, GraphActivity.this, bluetoothData);
//        LeadElevenLineChart leadElevenLineChart = new LeadElevenLineChart(activityGraphBinding.lineChartEleven, GraphActivity.this, bluetoothData);
//        LeadTwelveLineChart leadTwelveLineChart = new LeadTwelveLineChart(activityGraphBinding.lineChartTwelve, GraphActivity.this, bluetoothData);
//
//        inputStreamData.start();
//        leadOneLineChart.start();
//        leadTwoLineChart.start();
//        leadThreeLineChart.start();
//        leadFourLineChart.start();
//        leadFiveLineChart.start();
//        leadSixLineChart.start();
//        leadSevenLineChart.start();
//        leadEightLineChart.start();
//        leadNineLineChart.start();
//        leadTenLineChart.start();
//        leadElevenLineChart.start();
//        leadTwelveLineChart.start();


        activityGraphBinding.lineChartOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  thread.interrupt();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

         }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
          super.onDestroy();
    }
}