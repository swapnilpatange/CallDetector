package com.example.ruchir.calldetector

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import android.support.v4.content.ContextCompat.startActivity
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.content.ComponentName
import android.app.Activity
import android.content.SharedPreferences


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            val intent = Intent()
            val manufacturer = android.os.Build.MANUFACTURER
            if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
                intent.component = ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity")
            } else if ("oppo".equals(manufacturer, ignoreCase = true)) {
                intent.component = ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity")
            } else if ("vivo".equals(manufacturer, ignoreCase = true)) {
                intent.component = ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity")
            } else if ("oneplus".equals(manufacturer, ignoreCase = true)) {
                intent.component = ComponentName("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListAct‌​ivity")
            }

            val list = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            if (list.size > 0) {
                startActivity(intent)
            }
        } catch (e: Exception) {
        }

        var telephonyManager: TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(CallStateListener(this), PhoneStateListener.LISTEN_CALL_STATE)

        var intentFilter: IntentFilter = IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL)
        registerReceiver(OutgoingReceiver(this), intentFilter)

        var intentFilter1: IntentFilter = IntentFilter(TelephonyManager.ACTION_PHONE_STATE_CHANGED)
        registerReceiver(PhoneStateReciever(), intentFilter1)
        val sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE)
        if (sp.getString("isnew", "0").equals("1")) {
            val name = sp.getString("name", "")
            val mobNumber = sp.getString("mobNumber", "")
            sp.edit().putString("isnew", "0")
            var service: RetrofitService = RetrofitService()
            service.completeQuestionnaire(name, mobNumber)
        }

        //service.completeQuestionnaire("swapnil","nsp")


    }
}
