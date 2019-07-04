package com.company.app.app

import android.app.Activity
import android.content.Context

class MyPreferenceStore(
    ctx: Context
) : PreferenceStore {
    companion object {
        private const val NAME = "NAME"
        private const val USERNAME_KEY = "USERNAME_KEY"
    }

    private val prefs = ctx.getSharedPreferences(NAME, Activity.MODE_PRIVATE)

    override var username: String?
        get() = prefs.getString(USERNAME_KEY, null)
        set(value) = prefs.edit().putString(USERNAME_KEY, value).apply()
}