package com.example.ecgmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActionBar;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ecgmonitor.classes.InputStreamData;
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
import com.example.ecgmonitor.databinding.ActivityLineChartBinding;
import com.example.ecgmonitor.model.BluetoothData;
import com.example.ecgmonitor.threads.AVF;
import com.example.ecgmonitor.threads.AVL;
import com.example.ecgmonitor.threads.AVR;
import com.example.ecgmonitor.threads.Lead1;
import com.example.ecgmonitor.threads.Lead2;
import com.example.ecgmonitor.threads.Lead3;
import com.example.ecgmonitor.threads.V1;
import com.example.ecgmonitor.threads.V2;
import com.example.ecgmonitor.threads.V3;
import com.example.ecgmonitor.threads.V4;
import com.example.ecgmonitor.threads.V5;
import com.example.ecgmonitor.threads.V6;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.charts.LineChart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class LineChartActivity extends AppCompatActivity {

    ActivityLineChartBinding activityLineChartBinding;
    LineChart lead1LineChart, lead2LineChart, lead3LineChart, aVFLineChart, aVRLineChart, aVLLineChart, v1LineChart, v2LineChart, v3LineChart, v4LineChart, v5LineChart, v6LineChart;
    Lead1 lead1;
    Lead2 lead2;
    Lead3 lead3;
    AVF avf;
    AVL avl;
    AVR avr;
    V1 v1;
    V2 v2;
    V3 v3;
    V4 v4;
    V5 v5;
    V6 v6;

    LinearLayout parentLinearLayout;
    LinearLayout childLinearLayout1;
    LinearLayout childLinearLayout2;
    LinearLayout childLinearLayout3;
    LinearLayout childLinearLayout4;

    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    BluetoothManager bluetoothManager;
    ListView lv;
    String DeviceName = "Medical_Device";
    String UUID = "00001101-0000-1000-8000-00805f9b34fb";

    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    InputStreamReader inputStreamReader;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    int check = 0;

    BluetoothData bluetoothData;
    InputStreamData inputStreamData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLineChartBinding = ActivityLineChartBinding.inflate(getLayoutInflater());
        setContentView(activityLineChartBinding.getRoot());

        bluetoothData = new BluetoothData();

        parentLinearLayout = new LinearLayout(this);
        childLinearLayout1 = new LinearLayout(this);
        childLinearLayout2 = new LinearLayout(this);
        childLinearLayout3 = new LinearLayout(this);
        childLinearLayout4 = new LinearLayout(this);

        lead1LineChart = new LineChart(this);
        lead2LineChart = new LineChart(this);
        lead3LineChart = new LineChart(this);
        aVFLineChart = new LineChart(this);
        aVRLineChart = new LineChart(this);
        aVLLineChart = new LineChart(this);
        v1LineChart = new LineChart(this);
        v2LineChart = new LineChart(this);
        v3LineChart = new LineChart(this);
        v4LineChart = new LineChart(this);
        v5LineChart = new LineChart(this);
        v6LineChart = new LineChart(this);

        ecgEkg();

        activityLineChartBinding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here

                if(position == 0 && ++check > 1) {
                    bluetoothData.reinitialize();
                    ecgEkg();
                }
                else if(position == 1) {
                    bluetoothData.reinitialize();
                    limbLeads();
                }
                else if(position == 2) {
                    bluetoothData.reinitialize();
                    precordialLeads();
                }
                else if(position == 3) {
                    bluetoothData.reinitialize();
                    leadOne();
                }
                else if(position == 4) {
                    bluetoothData.reinitialize();
                    leadTwo();
                }
                else if(position == 5) {
                    bluetoothData.reinitialize();
                    leadThree();
                }
                else if(position == 6) {
                    bluetoothData.reinitialize();
                    leadAVF();
                }
                else if(position == 7) {
                    bluetoothData.reinitialize();
                    leadAVR();
                }
                else if(position == 8) {
                    bluetoothData.reinitialize();
                    leadAVL();
                }
                else if(position == 9) {
                    bluetoothData.reinitialize();
                    leadV1();
                }
                else if(position == 10) {
                    bluetoothData.reinitialize();
                    leadV2();
                }
                else if(position == 11) {
                    bluetoothData.reinitialize();
                    leadV3();
                }
                else if(position == 12) {
                    bluetoothData.reinitialize();
                    leadV4();
                }
                else if(position == 13) {
                    bluetoothData.reinitialize();
                    leadV5();
                }
                else if(position == 14) {
                    bluetoothData.reinitialize();
                    leadV6();
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        activityLineChartBinding.settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(LineChartActivity.this);
                dialog.setContentView(R.layout.setting);
                // dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                //  dialog.setCancelable(false);
                dialog.getWindow().getAttributes().windowAnimations = R.style.animation;

                Button addDevice = dialog.findViewById(R.id.add_device_btn);
                Button pairedDevice = dialog.findViewById(R.id.paired_device_btn);

                addDevice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(LineChartActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                ActivityCompat.requestPermissions(LineChartActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
                                return;
                            }
                            bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                            if (Build.VERSION.SDK_INT >= 31) {
                                bluetoothAdapter = bluetoothManager.getAdapter();
                            } else {
                                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            }

                            if (!bluetoothAdapter.isEnabled()) {
                                bluetoothAdapter.enable();
                            }
                        }

                        dialog.dismiss();

                    }
                });

                pairedDevice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ContextCompat.checkSelfPermission(LineChartActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                                ActivityCompat.requestPermissions(LineChartActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
                                ActivityCompat.requestPermissions(LineChartActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);

                                return;
                            }
                            bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                            if (Build.VERSION.SDK_INT >= 31) {
                                bluetoothAdapter = bluetoothManager.getAdapter();
                            } else {
                                bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                            }

                            if (!bluetoothAdapter.isEnabled()) {
                                bluetoothAdapter.enable();
                            }
                        }
                        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);

//                if (Build.VERSION.SDK_INT >= 31) {
                        bluetoothAdapter = bluetoothManager.getAdapter();
//
//                } else {
                        //           bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        //   }

                        if (!bluetoothAdapter.isEnabled()) {
                            bluetoothAdapter.enable();
                        }

                        pairedDevices = bluetoothAdapter.getBondedDevices();
                        String[] strings = new String[pairedDevices.size()];
                        int index = 0;
                        if (pairedDevices.size() > 0) {
                            for (BluetoothDevice device : pairedDevices) {
                                strings[index] = device.getName();
                                index++;
                                java.util.UUID uuid = device.getUuids()[0].getUuid();
                                Log.d("UUID", "Name " + device.getName() + "UUID " + uuid);
                                if (device.getName().equals("Medical_Device")) {
                                    mmDevice = device;
                                    inputStreamData = null;
                                    try {
                                        inputStreamData = new InputStreamData(LineChartActivity.this, bluetoothData, mmDevice);
                                        inputStreamData.start();
                                        dialog.dismiss();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                });

                dialog.show();


            }

        });


    }




    public void ecgEkg()
    {
        stopThread();
        removeViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parentLinearLayout.setWeightSum(4);
        parentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLinearLayout.setLayoutParams(params);

        //first column
        LinearLayout.LayoutParams childParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        childParams1.weight=1;
        childLinearLayout1.setWeightSum(3);
        childLinearLayout1.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout1.setLayoutParams(childParams1);

        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0);
        viewParams.weight=1;
        lead1LineChart.setLayoutParams(viewParams);
        lead2LineChart.setLayoutParams(viewParams);
        lead3LineChart.setLayoutParams(viewParams);

        childLinearLayout1.addView(lead1LineChart);
        childLinearLayout1.addView(lead2LineChart);
        childLinearLayout1.addView(lead3LineChart);

        parentLinearLayout.addView(childLinearLayout1);

        //second column

        childLinearLayout2.setWeightSum(3);
        childLinearLayout2.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout2.setLayoutParams(childParams1);
        aVRLineChart.setLayoutParams(viewParams);
        aVLLineChart.setLayoutParams(viewParams);
        aVFLineChart.setLayoutParams(viewParams);

        childLinearLayout2.addView(aVRLineChart);
        childLinearLayout2.addView(aVLLineChart);
        childLinearLayout2.addView(aVFLineChart);

        parentLinearLayout.addView(childLinearLayout2);


        //third column

        childLinearLayout3.setWeightSum(3);
        childLinearLayout3.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout3.setLayoutParams(childParams1);
        v1LineChart.setLayoutParams(viewParams);
        v2LineChart.setLayoutParams(viewParams);
        v3LineChart.setLayoutParams(viewParams);

        childLinearLayout3.addView(v1LineChart);
        childLinearLayout3.addView(v2LineChart);
        childLinearLayout3.addView(v3LineChart);

        parentLinearLayout.addView(childLinearLayout3);



        //forth column

        childLinearLayout4.setWeightSum(3);
        childLinearLayout4.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout4.setLayoutParams(childParams1);
        v4LineChart.setLayoutParams(viewParams);
        v5LineChart.setLayoutParams(viewParams);
        v6LineChart.setLayoutParams(viewParams);

        childLinearLayout4.addView(v4LineChart);
        childLinearLayout4.addView(v5LineChart);
        childLinearLayout4.addView(v6LineChart);

        parentLinearLayout.addView(childLinearLayout4);


        activityLineChartBinding.container.addView(parentLinearLayout);


        lead1 = new Lead1( lead1LineChart, this, bluetoothData);
        lead2 = new Lead2( lead2LineChart, this, bluetoothData);
        lead3 = new Lead3( lead3LineChart, this, bluetoothData);
        avl = new AVL(aVLLineChart, this, bluetoothData);
        avr = new AVR( aVRLineChart, this,bluetoothData);
        avf = new AVF(aVFLineChart, this, bluetoothData);
        v1 = new V1(v1LineChart, this, bluetoothData);
        v2 = new V2(v2LineChart, this, bluetoothData);
        v3 = new V3(v3LineChart, this, bluetoothData);
        v4 = new V4(v4LineChart, this, bluetoothData);
        v5 = new V5(v5LineChart, this, bluetoothData);
        v6 = new V6(v6LineChart, this, bluetoothData);


         lead1.start();
        lead2.start();
        lead3.start();
        avl.start();
        avr.start();
        avf.start();
        v1.start();
        v2.start();
        v3.start();
        v4.start();
        v5.start();
        v6.start();


    }


    public void limbLeads()
    {
        stopThread();
        removeViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parentLinearLayout.setWeightSum(2);
        parentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLinearLayout.setLayoutParams(params);

        //first column

        LinearLayout.LayoutParams childParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        childParams1.weight=1;
        childLinearLayout1.setWeightSum(3);
        childLinearLayout1.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout1.setLayoutParams(childParams1);

        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0);
        viewParams.weight=1;
        lead1LineChart.setLayoutParams(viewParams);
        lead2LineChart.setLayoutParams(viewParams);
        lead3LineChart.setLayoutParams(viewParams);

        childLinearLayout1.addView(lead1LineChart);
        childLinearLayout1.addView(lead2LineChart);
        childLinearLayout1.addView(lead3LineChart);

        parentLinearLayout.addView(childLinearLayout1);

        //second column

        childLinearLayout2.setWeightSum(3);
        childLinearLayout2.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout2.setLayoutParams(childParams1);
        aVRLineChart.setLayoutParams(viewParams);
        aVLLineChart.setLayoutParams(viewParams);
        aVFLineChart.setLayoutParams(viewParams);

        childLinearLayout2.addView(aVRLineChart);
        childLinearLayout2.addView(aVLLineChart);
        childLinearLayout2.addView(aVFLineChart);

        parentLinearLayout.addView(childLinearLayout2);
        activityLineChartBinding.container.addView(parentLinearLayout);



        lead1 = new Lead1( lead1LineChart, this, bluetoothData);
        lead2 = new Lead2( lead2LineChart, this, bluetoothData);
        lead3 = new Lead3( lead3LineChart, this, bluetoothData);
        avl = new AVL(aVLLineChart, this, bluetoothData);
        avr = new AVR( aVRLineChart, this, bluetoothData);
        avf = new AVF(aVFLineChart, this, bluetoothData);


        lead1.start();
        lead2.start();
        lead3.start();
        avl.start();
        avr.start();
        avf.start();



    }

    public void precordialLeads()
    {
        stopThread();
        removeViews();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        parentLinearLayout.setWeightSum(2);
        parentLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
        parentLinearLayout.setLayoutParams(params);

        //first column

        LinearLayout.LayoutParams childParams1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
        childParams1.weight=1;
        childLinearLayout1.setWeightSum(3);
        childLinearLayout1.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout1.setLayoutParams(childParams1);

        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0);
        viewParams.weight=1;
        v1LineChart.setLayoutParams(viewParams);
        v2LineChart.setLayoutParams(viewParams);
        v3LineChart.setLayoutParams(viewParams);

        childLinearLayout1.addView(v1LineChart);
        childLinearLayout1.addView(v2LineChart);
        childLinearLayout1.addView(v3LineChart);

        parentLinearLayout.addView(childLinearLayout1);

        //second column

        childLinearLayout2.setWeightSum(3);
        childLinearLayout2.setOrientation(LinearLayout.VERTICAL);
        childLinearLayout2.setLayoutParams(childParams1);
        v4LineChart.setLayoutParams(viewParams);
        v5LineChart.setLayoutParams(viewParams);
        v6LineChart.setLayoutParams(viewParams);

        childLinearLayout2.addView(v4LineChart);
        childLinearLayout2.addView(v5LineChart);
        childLinearLayout2.addView(v6LineChart);

        parentLinearLayout.addView(childLinearLayout2);
        activityLineChartBinding.container.addView(parentLinearLayout);

        v1 = new V1(v1LineChart, this, bluetoothData);
        v2 = new V2(v2LineChart, this, bluetoothData);
        v3 = new V3(v3LineChart, this, bluetoothData);
        v4 = new V4(v4LineChart, this, bluetoothData);
        v5 = new V5(v5LineChart, this, bluetoothData);
        v6 = new V6(v6LineChart, this, bluetoothData);


        v1.start();
        v2.start();
        v3.start();
        v4.start();
        v5.start();
        v6.start();
    }

    public void leadOne()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lead1LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(lead1LineChart);
        lead1 = new Lead1(lead1LineChart, this, bluetoothData);
        lead1.start();

    }

    public void leadTwo()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lead2LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(lead2LineChart);
        lead2 = new Lead2(lead2LineChart, this, bluetoothData);
        lead2.start();
    }

    public void leadThree()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        lead3LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(lead3LineChart);
        lead3 = new Lead3(lead3LineChart, this, bluetoothData);
        lead3.start();
    }
    public void leadAVF()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        aVFLineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(aVFLineChart);
        avf = new AVF(aVFLineChart, this, bluetoothData);
        avf.start();
    }

    public void leadAVR()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        aVRLineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(aVRLineChart);
        avr = new AVR(aVRLineChart, this, bluetoothData);
        avr.start();
    }

    public void leadAVL()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        aVLLineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(aVLLineChart);
        avl = new AVL(aVLLineChart, this, bluetoothData);
        avl.start();
    }
    public void leadV1()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v1LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(v1LineChart);
        v1 = new V1(v1LineChart, this, bluetoothData);
        v1.start();
    }

    public void leadV2()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v2LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(v2LineChart);
        v2 = new V2(v2LineChart, this, bluetoothData);
        v2.start();
    }

    public void leadV3()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v3LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(v3LineChart);
        v3 = new V3(v3LineChart, this, bluetoothData);
        v3.start();
    }
    public void leadV4()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v4LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(v4LineChart);
        v4 = new V4(v4LineChart, this, bluetoothData);
        v4.start();
    }

    public void leadV5()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v5LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(v5LineChart);
        v5 = new V5(v5LineChart, this, bluetoothData);
        v5.start();
    }

    public void leadV6()
    {
        stopThread();
        removeViews();
        LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        v6LineChart.setLayoutParams(viewParams);
        activityLineChartBinding.container.addView(v6LineChart);
        v6 = new V6(v6LineChart, this, bluetoothData);
        v6.start();
    }

    public void removeViews()
    {
        childLinearLayout1.removeAllViews();
        childLinearLayout2.removeAllViews();
        childLinearLayout3.removeAllViews();
        childLinearLayout4.removeAllViews();
        parentLinearLayout.removeAllViews();
        activityLineChartBinding.container.removeAllViews();
    }

    public void stopThread()
    {
        if(lead1 != null)
            lead1.setStopped();
        if(lead2 != null)
            lead2.setStopped();
        if(lead3 != null)
            lead3.setStopped();
        if(avl != null)
            avl.setStopped();
        if(avr != null)
            avr.setStopped();
        if(avf != null)
            avf.setStopped();
        if(v1 != null)
            v1.setStopped();
        if(v2 != null)
            v2.setStopped();
        if(v3 != null)
            v3.setStopped();
        if(v4 != null)
            v4.setStopped();
        if(v5 != null)
            v5.setStopped();
        if(v6 != null)
            v6.setStopped();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopThread();
    }


}