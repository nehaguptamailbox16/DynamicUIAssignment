package com.example.hubblerassigment.utils

import android.content.Context

object SharedPreferenceHelper {

    fun putSharedPreference(context: Context, key: String, objectValue: Int) {
        val sharedPreferences = context.getSharedPreferences(Constant.FILE_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(key,objectValue)
        editor.apply()
        editor.commit()
    }

     fun getSharedPreference(context: Context, key: String?, defaultObject: Int): Int {
        val sharedPreferences = context.getSharedPreferences(Constant.FILE_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(key, defaultObject)
    }
}