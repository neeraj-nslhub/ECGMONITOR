package com.example.ecgmonitor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ecgmonitor.databinding.ActivityLandingBinding;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;


public class LandingActivity extends AppCompatActivity {

    ActivityLandingBinding activityLandingBinding;
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
    public static int mNumber0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLandingBinding = ActivityLandingBinding.inflate(getLayoutInflater());
        setContentView(activityLandingBinding.getRoot());

        activityLandingBinding.newPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });

        activityLandingBinding.existingPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LandingActivity.this, LineChartActivity.class);
                startActivity(intent);


            }
        });


        activityLandingBinding.addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(LandingActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(LandingActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
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
            }
        });

        activityLandingBinding.pairedDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(LandingActivity.this, android.Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_DENIED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        ActivityCompat.requestPermissions(LandingActivity.this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 2);
                        ActivityCompat.requestPermissions(LandingActivity.this, new String[]{Manifest.permission.BLUETOOTH_SCAN}, 2);

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
                        UUID uuid = device.getUuids()[0].getUuid();
                        Log.d("UUID", "Name " + device.getName() + "UUID " + uuid);
                        if (device.getName().equals("Medical_Device")) {
                            mmDevice = device;
                            break;
                        }


                    }
                  //  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                 //   activityLandingBinding.listView.setAdapter(arrayAdapter);
                    try {
                        openBT();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        });


    }

    void openBT() throws IOException {
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
       // mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();

        beginListenForData();

      //  myLabel.setText("Bluetooth Opened");
    }

    void beginListenForData()
        {
          final Handler handler = new Handler();
          final byte delimiter = 10; //This is the ASCII code for a newline character

           stopWorker = false;
           readBufferPosition = 0;
           readBuffer = new byte[1024];
           workerThread = new Thread(new Runnable()
           {
            public void run()
            {
              while(!Thread.currentThread().isInterrupted() && !stopWorker)
             {
               try
                 {
                  int bytesAvailable = mmInputStream.available();
                  if(bytesAvailable > 0)
                  {
                      BufferedReader reader = new BufferedReader(new InputStreamReader(mmInputStream));
                      reader.readLine();

                      String line;
                      while ((line = reader.readLine()) != null)
                      {
                          String[] row = line.split(",");
                          double column0 = Double.parseDouble(row[0]);
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
                        //  mNumber0 = column0;
                          Log.d("BluetoothSerialData","PositionO "+column0 );
                          Log.d("BluetoothSerialData","Position1 "+column1 );
                          Log.d("BluetoothSerialData","Position2 "+column2 );
                          Log.d("BluetoothSerialData","Position3 "+column3 );
                          Log.d("BluetoothSerialData","Position4 "+column4 );
                          Log.d("BluetoothSerialData","Position5 "+column5 );
                          Log.d("BluetoothSerialData","Position6 "+column6 );
                          Log.d("BluetoothSerialData","Position7 "+column7 );
                          Log.d("BluetoothSerialData","Position8 "+column8 );
                          Log.d("BluetoothSerialData","Position9 "+column9 );
                          Log.d("BluetoothSerialData","Position1O "+column10 );
                          Log.d("BluetoothSerialData","Position11 "+column11 );



                      }
//                    byte[] packetBytes = new byte[bytesAvailable];
//                    mmInputStream.read(packetBytes);
//                    for(int i=0;i<bytesAvailable;i++)
//                     {
//                       byte b = packetBytes[i];
//                       if(b == delimiter)
//                       {
//                        byte[] encodedBytes = new byte[readBufferPosition];
//                        System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
//                        final String data = new String(encodedBytes, "US-ASCII");
//                        readBufferPosition = 0;
//                        Log.d("BluetoothSerialData","BluetoothSerialData"+data );
////                       handler.post(new Runnable()
////                      {
////                          public void run()
////                           {
////                            myLabel.setText(data);
////                           }
////                      });
//                }
//                  else
//                       {
//                           readBuffer[readBufferPosition++] = b;
//                       }
//                       }
                       }
                       }
                          catch (IOException ex)
                               {
                               stopWorker = true;
                               }
                               }
                              }
                             });

                       workerThread.start();
                    }


            void closeBT() throws IOException
                   {
                       stopWorker = true;
                         mmOutputStream.close();
                        mmInputStream.close();
                        mmSocket.close();
                 //    myLabel.setText("Bluetooth Closed");
                    }

}


