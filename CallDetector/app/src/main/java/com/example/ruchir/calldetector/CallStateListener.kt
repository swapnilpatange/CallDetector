package com.example.ruchir.calldetector

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.widget.Toast
import android.provider.ContactsContract
import android.media.AudioManager
import com.android.internal.telephony.ITelephony


class CallStateListener : PhoneStateListener {
    var activity: Activity?

    constructor(activity: Activity? = null) : super() {
        this.activity = activity
    }

    override fun onCallStateChanged(state: Int, incomingNumber: String?) {
        when (state) {
            TelephonyManager.CALL_STATE_RINGING -> {
                var blockNumber: String = "+917738471423"

                val audioManager = (activity as Context).getSystemService(Context.AUDIO_SERVICE) as AudioManager
                //Turn ON the mute
                audioManager.setStreamMute(AudioManager.STREAM_RING, true)
                val telephonyManager = (activity as Context).getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                try {

                    val clazz = Class.forName(telephonyManager.javaClass.name)
                    val method = clazz.getDeclaredMethod("getITelephony")
                    method.isAccessible = true
                    var telephonyService: ITelephony = method.invoke(telephonyManager) as ITelephony
                    //Checking incoming call number
                    //val telephonyService = m.invoke(tm) // Get the internal ITelephony object

                    if (incomingNumber.equals(blockNumber)) {
                        //telephonyService.silenceRinger();//Security exception problem
                        var c = Class.forName(telephonyService.javaClass.getName()) // Get its class
                        var m = c.getDeclaredMethod("endCall") // Get the "endCall()" method
                        m.setAccessible(true) // Make it accessible
                        m.invoke(telephonyService)
                    }
                } catch (e: Exception) {
                    Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show()
                }

                //Turn OFF the mute
                audioManager.setStreamMute(AudioManager.STREAM_RING, false)
                Toast.makeText(activity,
                        "Incoming: " + incomingNumber + "," + getContactName(incomingNumber!!, activity as Context),
                        Toast.LENGTH_LONG).show();
            }
        }
        super.onCallStateChanged(state, incomingNumber)
    }

    fun getContactName(phoneNumber: String, context: Context): String {
        val uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber))

        val projection = arrayOf(ContactsContract.PhoneLookup.DISPLAY_NAME)

        var contactName = ""
        val cursor = context.getContentResolver().query(uri, projection, null, null, null)

        if (cursor != null) {
            if (cursor!!.moveToFirst()) {
                contactName = cursor!!.getString(0)
            }
            cursor!!.close()
        }

        return contactName
    }
}