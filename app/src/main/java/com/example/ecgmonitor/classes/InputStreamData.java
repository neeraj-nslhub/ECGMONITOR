package com.example.ecgmonitor.classes;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
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


    String DeviceName = "Medical_Device";
    String UUID = "00001101-0000-1000-8000-00805f9b34fb";


    BluetoothSocket mSocket;
    BluetoothDevice mDevice;


    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;

    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;

    private Set<BluetoothDevice> pairedDevices;


    public InputStreamData(Context context, BluetoothData bluetoothData) throws IOException {
        mContext = context;
        mData = bluetoothData;

        bluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);

//                if (Build.VERSION.SDK_INT >= 31) {
        bluetoothAdapter = bluetoothManager.getAdapter();
//
//                } else {
        //           bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //   }

        if (!bluetoothAdapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            bluetoothAdapter.enable();
        }

        pairedDevices = bluetoothAdapter.getBondedDevices();
        String[] strings = new String[pairedDevices.size()];
        int index = 0;
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                strings[index] = device.getName();
                index++;
                UUID uuid = device.getUuids()[0].getUuid();
                Log.d("UUID", "Name " + device.getName() + "UUID " + uuid);
                if (device.getName().equals("Medical_Device")) {
                    mDevice = device;
                    break;
                }


            }
        }

            UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }


            mSocket = mDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            mInputStream = mSocket.getInputStream();
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

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
                            String[] row = line.split(",");
                            int column0 = Integer.parseInt(row[0]);
                            int column1 = Integer.parseInt(row[1]);
                            int column2 = Integer.parseInt(row[2]);
                            int column3 = Integer.parseInt(row[3]);
                            int column4 = Integer.parseInt(row[4]);
                            int column5 = Integer.parseInt(row[5]);
                            int column6 = Integer.parseInt(row[6]);
                            int column7 = Integer.parseInt(row[7]);
                            int column8 = Integer.parseInt(row[8]);
                            int column9 = Integer.parseInt(row[9]);
                            int column10 = Integer.parseInt(row[10]);
                            int column11 = Integer.parseInt(row[11]);

                            Log.d("BluetoothSerialData", "PositionO " + column0);
                            Log.d("BluetoothSerialData", "Position1 " + column1);
                            Log.d("BluetoothSerialData", "Position2 " + column2);
                            Log.d("BluetoothSerialData", "Position3 " + column3);
                            Log.d("BluetoothSerialData", "Position4 " + column4);
                            Log.d("BluetoothSerialData", "Position5 " + column5);
                            Log.d("BluetoothSerialData", "Position6 " + column6);
                            Log.d("BluetoothSerialData", "Position7 " + column7);
                            Log.d("BluetoothSerialData", "Position8 " + column8);
                            Log.d("BluetoothSerialData", "Position9 " + column9);
                            Log.d("BluetoothSerialData", "Position1O " + column10);
                            Log.d("BluetoothSerialData", "Position11 " + column11);

                        }

                    }
                } catch (IOException ex) {
                    stopWorker = true;
                }
            }

        }


    }
