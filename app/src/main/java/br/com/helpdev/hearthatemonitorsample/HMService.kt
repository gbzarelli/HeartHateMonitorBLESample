package br.com.helpdev.hearthatemonitorsample

import android.app.Service
import android.bluetooth.*
import android.content.Intent
import android.os.IBinder
import android.util.Log
import java.util.*


class HMService : Service(), HMGatt.HMGattCallback {

    companion object {
        const val LOG = "HMService"

        const val HR_SENSOR_ADDRESS = "33:FF:9F:FA:FF:FF"
        /*
        0x180D / 0000180d-0000-1000-8000-00805f9b34fb - HEART_RATE_SERVICE_UUID
        0x2A37 / 00002a37-0000-1000-8000-00805f9b34fb - HEART_RATE_MEASUREMENT_CHAR_UUID
        0x2A39 / 00002a39-0000-1000-8000-00805f9b34fb - HEART_RATE_CONTROL_POINT_CHAR_UUID
        0x2902 / 00002902-0000-1000-8000-00805f9b34fb - CLIENT_CHARACTERISTIC_CONFIG_UUID
        */
        val HEART_RATE_SERVICE_UUID = UUID.fromString("0000180d-0000-1000-8000-00805f9b34fb")!!
        val HEART_RATE_MEASUREMENT_CHAR_UUID = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb")!!
        val HEART_RATE_CONTROL_POINT_CHAR_UUID = UUID.fromString("00002a39-0000-1000-8000-00805f9b34fb")!!
        val CLIENT_CHARACTERISTIC_CONFIG_UUID = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb")!!
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothDevice: BluetoothDevice? = null
    private var hmGatt: HMGatt? = null

    override fun onCreate() {
        super.onCreate()
        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
        bluetoothDevice = bluetoothAdapter!!.getRemoteDevice(HR_SENSOR_ADDRESS)

        if (bluetoothDevice == null) {
            Log.d(LOG, "BluetoothDevice == null")
        } else {
            hmGatt = HMGatt(this, bluetoothDevice!!, bluetoothAdapter!!, this, true)
            hmGatt!!.connectToDevice()
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        Log.d(LOG, "onDestroy")
        hmGatt?.disconnectDevice()
        super.onDestroy()
    }

    override fun heartRate(heartRate: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun statusHMGatt(status: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}