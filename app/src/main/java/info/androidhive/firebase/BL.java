package info.androidhive.firebase;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import javax.xml.datatype.Duration;

public class BL extends AppCompatActivity {
    private boolean CONTINUE_READ_WRITE = true;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bl);
        registerReceiver(discoveryResult, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter != null && adapter.isDiscovering()) {
            adapter.cancelDiscovery();
        }
        adapter.startDiscovery();
textView = (TextView) findViewById(R.id.textView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            unregisterReceiver(discoveryResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (socket != null) {
            try {
                is.close();
                os.close();
                socket.close();
                CONTINUE_READ_WRITE = false;
            } catch (Exception e) {
            }
        }
    }

    private BluetoothSocket socket;
    private OutputStreamWriter os;
    private InputStream is;
    private BluetoothDevice remoteDevice;
    private BroadcastReceiver discoveryResult = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            android.util.Log.e("TrackingFlow", "WWWTTTFFF");

            unregisterReceiver(this);
            remoteDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

                Toast.makeText(getApplicationContext(), "  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT).show();
                new Thread(reader).start();
            }

        }

    }   ;


        private Runnable reader = new Runnable() {

            @Override
            public void run() {
                try {
                    android.util.Log.e("TrackingFlow", "Found: " + remoteDevice.getName());
                //    Toast.makeText(BL.this, "TrackingFlow Found"+ remoteDevice.getName(), Toast.LENGTH_LONG).show();

                    synchronized (this) {
                        wait(5000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                textView.setText("TrackingFlowFound: " + remoteDevice.getName()+"\n \n");
                            }
                        });
                    }
                    UUID uuid = UUID.fromString("4e5d48e0-75df-11e3-981f-0800200c9a66");
                    try {
                        socket = remoteDevice.createRfcommSocketToServiceRecord(uuid);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    socket.connect();
                    android.util.Log.e("TrackingFlow", "Connected...");
                  //  Toast.makeText(this,"TrackingFlow", "Connected...", Duration.l)
                  //  Toast.makeText(BL.this, "TrackingFlow Connected...", Toast.LENGTH_LONG).show();
                    //textView.append("TrackingFlow Connected...\n \n");
                    synchronized (this) {
                        wait(5000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView.append("TrackingFlow Connected...\n \n");

                            }
                        });
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }

        };



    }

