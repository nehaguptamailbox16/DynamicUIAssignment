package com.example.hubblerassigment.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.hubblerassigment.model.UserForm
import com.example.hubblerassigment.repository.UserFormRepository

class UserFormViewModel(application: Application) : AndroidViewModel(application) {
        val context : Context = application

    fun dynamicUserForm(): LiveData<List<UserForm>> {
        return UserFormRepository(context).getUserForm()
    }

    fun validateAge(min: Int, max: Int, age:String): Boolean{
        return age.toInt() in min..max
    }

    fun checkRequiredField(inputText : String):Boolean{
        return (inputText!="" && inputText!=null)
    }
}