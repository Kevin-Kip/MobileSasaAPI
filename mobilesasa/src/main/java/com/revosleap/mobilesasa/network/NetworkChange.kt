package com.revosleap.mobilesasa.network

interface NetworkChange {
    fun networkChanged(connected: Boolean)
}