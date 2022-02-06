package com.example.hubblerassigment.model

import com.google.gson.annotations.SerializedName


data class UserForm(

    @SerializedName("field-name" ) val fieldName : String,
    @SerializedName("type"       ) val type: String,
    @SerializedName("required") val required: String,
    @SerializedName("min") val min: Int,
    @SerializedName("max") val max: Int,
    @SerializedName("options") val options: List<String>
    )


