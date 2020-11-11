package com.tatvasoftpractical.base

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class MyBaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private var instance: MyBaseApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        var tempPref: SharedPreferences? = null
        fun getPref(): SharedPreferences? {
            return tempPref
        }

        var context: Context? = null
        fun getContextApp(): Context? {
            return context;
        }
    }
}