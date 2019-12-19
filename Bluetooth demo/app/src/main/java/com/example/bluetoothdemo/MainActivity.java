package com.example.bluetoothdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Button searchButton;
    TextView textView;
    BluetoothAdapter bluetoothAdapter;
    ArrayList<String> bluetoothDevices=new ArrayList<>();
    ArrayList<String> addressList=new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    private final BroadcastReceiver broadcastReceiver= new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            Log.i("Action:",action);

            if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action))
            {
                textView.setText("Finished");
                searchButton.setEnabled(true);
            }
            else if(BluetoothDevice.ACTION_FOUND.equals(action))
            {
                BluetoothDevice device= intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String address=device.getAddress();
                String name=device.getName();
                String rssi= String.format("%s", intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE));
                Log.i("Device Found","Name: "+ name +" Address: " + address + " RSSI: "+ rssi);

                String deviceString="";

                if(addressList.contains(address))
                {
                    addressList.add(address);
                    if(name==null || name.equals(""))
                    {
                        deviceString=address+ " - " + " RSSI " + rssi + " dBm";
                    }
                    else {
                        deviceString = name+ " - " + " RSSI " + rssi + " dBm";
                    }


                    bluetoothDevices.add(deviceString);

                    arrayAdapter.notifyDataSetChanged();
                }

            }
        }
    };

    public void searchClicked(View view)
    {
        textView.setText("Searching...");
        searchButton.setEnabled(false);
        bluetoothAdapter.startDiscovery();
        bluetoothDevices.clear();
        addressList.clear();
        Log.i("Button:","pessed");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.bluetoothListView);
        searchButton=findViewById(R.id.searchButton);
        textView=findViewById(R.id.statusTextView);


        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,bluetoothDevices);
        listView.setAdapter(arrayAdapter);

        bluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        intentFilter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(broadcastReceiver,intentFilter);


    }
}
