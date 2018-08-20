package com.pengdaniel.imnapping.model

import android.content.Context
import android.content.SharedPreferences

class SharedPrefManager(context: Context) {

    private var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE)
    }

    inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    fun getDefaultMessage() : String = sharedPreferences.getString(DEFAULT_MSG_KEY, DEFAULT_SMS_MESSAGE)

    fun getMessage(incomingAddress: String) = sharedPreferences.getString(incomingAddress, getDefaultMessage())

    fun getReceiverStatus() = sharedPreferences.getBoolean(RECEIVER_STATUS_KEY, false)

    fun setDefaultMessage(newMessage: String) {
        sharedPreferences.edit { it.putString(DEFAULT_MSG_KEY, newMessage) }
    }

    fun setCustomMessage(address: String, newMessage: String) {
        sharedPreferences.edit { it.putString(address, newMessage) }
    }

    fun setReceiverStatus(newStatus: Boolean) {
        sharedPreferences.edit { it.putBoolean(RECEIVER_STATUS_KEY, newStatus) }
    }

    companion object {
        private const val DEFAULT_SMS_MESSAGE = "I'm napping right now"

        private const val SHARED_PREF_KEY = "com.pengdaniel.imnapping.PREFERENCE_FILE_KEY"
        private const val DEFAULT_MSG_KEY = "DEFAULT_MSG_KEY"
        private const val RECEIVER_STATUS_KEY = "RECEIVER_STATUS_KEY"
    }
}