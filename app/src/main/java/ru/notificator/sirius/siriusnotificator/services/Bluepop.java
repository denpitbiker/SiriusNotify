package ru.notificator.sirius.siriusnotificator.services;

import android.annotation.SuppressLint;
import android.bluetooth.*;


public class Bluepop {

    BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
    @SuppressLint("HardwareIds")
    String getBluetooth_mac(){
        return bluetooth.getAddress();
    }
}
