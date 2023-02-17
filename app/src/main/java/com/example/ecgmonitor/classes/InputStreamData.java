package com.example.ecgmonitor.classes;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.ecgmonitor.model.BluetoothData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

public class InputStreamData extends Thread {
    private BluetoothData mData;
    private String mStr;
    Context mContext;
    InputStream mInputStream;

    UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    BluetoothSocket mSocket;
    BluetoothDevice mDevice;


    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;



    public InputStreamData(Context context, BluetoothData bluetoothData, BluetoothDevice mmDevice) throws IOException {
        mContext = context;
        mData = bluetoothData;
        mDevice = mmDevice;

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
            mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            mInputStream = mSocket.getInputStream();
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

        }

    public static boolean isNumeric(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
        public void run() {

            while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                try {
                    int bytesAvailable = mInputStream.available();
                    if (bytesAvailable > 0) {
                        mData.put(mInputStream);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(mInputStream));
                        reader.readLine();

                        String line;
                        while ((line = reader.readLine()) != null) {
                            Log.d("InputStream", "run: "+stopWorker);
                            String[] row = line.split(",");
                            if(row.length>11) {
                                if(isNumeric(row[0]))
                                {
                                    float column0 = Float.parseFloat(row[0]);
                                    Log.d("BluetoothSerialData", "PllsitionO " + column0);
                                }
                                if(isNumeric(row[1]))
                                {
                                    float column1 = Float.parseFloat(row[1]);
                                    Log.d("BluetoothSerialData", "Pollsition1 " + column1);
                                }
                                if(isNumeric(row[2]))
                                {
                                    float column2 = Float.parseFloat(row[2]);
                                    Log.d("BluetoothSerialData", "Pqwosition2 " + column2);

                                }
                                if(isNumeric(row[3]))
                                {
                                    float column3 = Float.parseFloat(row[3]);
                                     Log.d("BluetoothSerialData", "Pefosition3 " + column3);

                                }
                                if(isNumeric(row[4]))
                                {
                                    float column4 = Float.parseFloat(row[4]);
                                      Log.d("BluetoothSerialData", "Pvfrosition4 " + column4);

                                }
                                if(isNumeric(row[5]))
                                {
                                    float column5 = Float.parseFloat(row[5]);
                                  Log.d("BluetoothSerialData", "Porcxsition5 " + column5);

                                }
                                if(isNumeric(row[6]))
                                {
                                    float column6 = Float.parseFloat(row[6]);
                                  Log.d("BluetoothSerialData", "Pxfvewosition6 " + column6);

                                }
                                if(isNumeric(row[7]))
                                {
                                    float column7 = Float.parseFloat(row[7]);
                                    Log.d("BluetoothSerialData", "Pyhbngcosition7 " + column7);
                                }
                                if(isNumeric(row[8]))
                                {
                                    float column8 = Float.parseFloat(row[8]);
                                    Log.d("BluetoothSerialData", "Poijufexsition8 " + column8);
                                }
                                if(isNumeric(row[9]))
                                {
                                    float column9 = Float.parseFloat(row[9]);
                                    Log.d("BluetoothSerialData", "Pyjcjiosition9 " + column9);
                                }
                                if(isNumeric(row[10]))
                                {
                                    float column10 = Float.parseFloat(row[10]);
                                    Log.d("BluetoothSerialData", "Punloivosition1O " + column10);
                                }
                                if(isNumeric(row[11]))
                                {
                                    float column11 = Float.parseFloat(row[11]);
                                    Log.d("BluetoothSerialData", "Prexhulnosition11 " + column11);
                                }

                            }

                        }

                    }
                } catch (IOException ex) {
                    stopWorker = true;
                }
            }
            Log.d("InputStream", "run: "+stopWorker);
        }


    }
