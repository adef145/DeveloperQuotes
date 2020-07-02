package com.adefruandta.devquotes.domain

import android.content.Context
import android.content.SharedPreferences

class Preferences(
    private val sharedPreferences: SharedPreferences
) {

    companion object {

        private var INSTANCE: Preferences? = null

        private const val QUOTE_ID = "QUOTE_ID"

        fun getInstance(context: Context): Preferences =
            INSTANCE ?: context.getSharedPreferences(
                "DeveloperQuotes",
                Context.MODE_PRIVATE
            )?.let {
                INSTANCE = Preferences(it)
                INSTANCE
            }!!

    }

    var quoteId: String?
        set(value) {
            sharedPreferences.edit().apply {
                putString(QUOTE_ID, value)
            }.apply()
        }
        get() {
            return sharedPreferences.getString(QUOTE_ID, null)
        }
}