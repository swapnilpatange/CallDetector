package com.example.ruchir.calldetector

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import android.telephony.TelephonyManager



class OutgoingReceiver : BroadcastReceiver {
    var activity: Activity?

    var start_time: Long = 0
    var end_time:Long = 0

    constructor(activity: Activity? = null) : super() {
        this.activity = activity
    }

    override fun onReceive(p0: Context?, intent: Intent?) {
        var number: String = intent!!.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        Toast.makeText(activity, "outgoing: " + number, Toast.LENGTH_LONG).show()
        var action:String=intent.action
        if (action.equals("android.intent.action.PHONE_STATE")){
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                            TelephonyManager.EXTRA_STATE_RINGING)) {
                start_time = System.currentTimeMillis()
            }
            if (intent.getStringExtra(TelephonyManager.EXTRA_STATE).equals(
                            TelephonyManager.EXTRA_STATE_IDLE)) {
                end_time = System.currentTimeMillis()
                //Total time talked =
                val total_time = end_time - start_time
                //Store total_time somewhere or pass it to an Activity using intent
            }
        }
    }
}