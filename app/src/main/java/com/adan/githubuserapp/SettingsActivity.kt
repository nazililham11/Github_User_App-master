package com.adan.githubuserapp

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setActionBarTitle("Settings")
    }


    private fun setActionBarTitle(title: String) {
        (supportActionBar as ActionBar)?.title = title
    }

    class SettingsFragment : PreferenceFragmentCompat(),  SharedPreferences.OnSharedPreferenceChangeListener {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
        }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            val alarmReceiver = AlarmReceiver()
            val isAlarmEnabled = context?.let { alarmReceiver.isAlarmSet(it, AlarmReceiver.TYPE_REPEATING) }
            val pref: SwitchPreferenceCompat? = findPreference("alarm")
            pref?.isChecked = isAlarmEnabled?:false
        }

        override fun onResume() {
            super.onResume()
            preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        }

        override fun onPause() {
            super.onPause()
            preferenceManager.sharedPreferences
                .unregisterOnSharedPreferenceChangeListener(this)
        }
        override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
            if (key.equals("alarm")) {
                val pref: SwitchPreferenceCompat? = findPreference(key!!)
                pref?.isChecked?.let { setAlarm(it) }
            }
        }

        fun setAlarm(state: Boolean){
            val repeatTime = "09:00"
            val repeatMessage = "Github User App Repeating Alarm At 09:00 AM"
            val alarmReceiver = AlarmReceiver()

            context?.let {
                if (state)
                    alarmReceiver.setRepeatingAlarm(it, AlarmReceiver.TYPE_REPEATING, repeatTime, repeatMessage)
                else
                    alarmReceiver.cancelAlarm(it, AlarmReceiver.TYPE_REPEATING)
            }

        }


    }
}