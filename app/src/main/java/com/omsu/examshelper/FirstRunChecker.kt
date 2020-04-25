package com.omsu.examshelper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.omsu.examshelper.parser.DatabaseController.dropDatabase

import com.omsu.examshelper.parser.DatabaseController.parseAndFill

private const val PREFS_NAME = "MyPrefsFile"
private const val PREF_VERSION_CODE_KEY = "version_code"
private const val DOESNT_EXIST = -1

object FirstRunChecker {
    fun checkFirstRun(context: Context): Unit {
        val currentVersionCode = BuildConfig.VERSION_CODE
        val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)
        if (currentVersionCode == savedVersionCode) {
            return
        } else if (savedVersionCode == DOESNT_EXIST) {
            parseAndFill(context)
        } else if (currentVersionCode > savedVersionCode) {
            dropDatabase(context)
            parseAndFill(context)
        }
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }
}