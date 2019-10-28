package com.revosleap.mobilesasa

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.util.Log
import com.revosleap.mobilesasa.network.NetworkListener

class MainClass : Application() {
    private var connectivityReceiver: NetworkListener? = null

    private fun getConnectivityReceiver(): NetworkListener {
        if (connectivityReceiver == null)
            connectivityReceiver = NetworkListener()
        return connectivityReceiver!!
    }

    override fun onCreate() {
        super.onCreate()
        registerConnectivityReceiver()
    }

    // register here your filters
    private fun registerConnectivityReceiver() {
        try {
            // if (android.os.Build.VERSION.SDK_INT >= 26) {
            val filter = IntentFilter()
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION)
            filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
            filter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
            registerReceiver(getConnectivityReceiver(), filter)
        } catch (e: Exception) {
            Log.e(TAG, e.message!!)
        }
    }
}