package com.example.hostbluetooth

import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.content.Context

class BluetoothService(val context: Context) : Thread() {


    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }


}