package com.example.hostbluetooth

import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.util.Log
import java.io.IOException
import java.util.UUID

class ConnectThread(
    context: Context,
    var status: ((String) -> Unit)? = null
) : Thread() {


    private val TAG: String = "Bluetooth"
    private var mSocket: BluetoothSocket? = null
    private var bluetoothServerSocket: BluetoothServerSocket? = null

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    init {
        try {
            Log.d("Bluetooth", "createListen...")
            bluetoothServerSocket = bluetoothAdapter?.listenUsingInsecureRfcommWithServiceRecord(
                "Bluetooth",
                UUID.fromString(MainActivity.uuid)
            )

            Log.d("Bluetooth", "Listen created")

        } catch (e: IOException) {
            Log.d("Bluetooth", "Not connect")
        } catch (e: SecurityException) {
            Log.d("Bluetooth", "Not security")
        }
    }

    override fun run() {
        while (true) {
            try {
                Log.d(TAG, " Run ")
                mSocket = bluetoothServerSocket?.accept()
                if (mSocket != null) {
                    readMessage()
                    Log.d(TAG, "connect complite")
                } else {
                    Log.d(TAG, "socket null")
                    break
                }
            } catch (e: IOException) {
                Log.d(TAG, "ex")
                break
            } catch (e: SecurityException) {
                Log.d(TAG, "secEx")
                break
            }
        }
    }

    fun closeConnection() {
        try {
            mSocket?.close()
        } catch (e: IOException) {

        }
    }

    fun readMessage() {
        val buffer = ByteArray(256)
        while (true) {
            try {
                val length = mSocket?.inputStream?.read(buffer)
                val message = String(buffer, 0, length ?: 0)
                Log.d(TAG, message)
                status?.invoke(message)
            } catch (e: IOException) {
                Log.d(TAG, e.message.toString())
                break
            }

        }
    }

}