package com.example.hubblerassigment.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.hubblerassigment.model.UserForm
import com.example.hubblerassigment.utils.Utils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class UserFormRepository(private val context :Context) {

    private val userFormData = MutableLiveData<List<UserForm>>()

    fun getUserForm() : LiveData<List<UserForm>> {
        val jsonFileString: String? =
            Utils.getJsonFromAssets(context, "UserScenarioOne.json")
        val listUserType: Type = object : TypeToken<List<UserForm?>?>() {}.type
        val users: List<UserForm> = Gson().fromJson(jsonFileString, listUserType)
        userFormData.value = users
        return userFormData
    }
}