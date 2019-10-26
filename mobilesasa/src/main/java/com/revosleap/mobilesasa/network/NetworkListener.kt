package com.revosleap.mobilesasa.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NetworkListener : BroadcastReceiver() {
    private var networkChange: NetworkChange? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        networkChange = context as NetworkChange
        networkChange?.networkChanged(Connectivity.isConnected(context))
    }
}