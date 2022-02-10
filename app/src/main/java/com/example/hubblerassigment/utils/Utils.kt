package com.example.hubblerassigment.utils

import android.content.Context
import android.text.Html
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.example.hubblerassigment.model.UserForm
import com.google.android.material.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.*

class Utils {

    companion object {
        fun getJsonFromAssets(context: Context, fileName: String?): String? {
            val jsonString: String
            try {
                val inputStream: InputStream = context.assets.open(fileName!!)
                val size: Int = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                jsonString = String(buffer)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
            return jsonString
        }


        fun storeDataInJSON(context: Context, userFormString: String){
            val file = File(context.filesDir,Constant.JSON_FILE_NAME)
            val previousJson: String?
            var newJsonObject: JSONObject? = null
            val jsonConstant : Int? = SharedPreferenceHelper.getSharedPreference(context,Constant.KEY_SHARED_PREFERENCE, 1)

            if (file.exists()) {
                try {
                    previousJson = readDataFromJSON(context)
                    newJsonObject = JSONObject(previousJson!!)
                             } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            else {
                SharedPreferenceHelper.putSharedPreference(context,Constant.KEY_SHARED_PREFERENCE, 1)
                newJsonObject = JSONObject()
            }
            try {

                newJsonObject?.put(jsonConstant.toString(), JSONArray(userFormString))
                SharedPreferenceHelper.putSharedPreference(context,Constant.KEY_SHARED_PREFERENCE, jsonConstant!! +1)

            } catch (e: JSONException) {
                e.printStackTrace()
            }
            val fileWriter = FileWriter(file)
            val bufferedWriter = BufferedWriter(fileWriter)
            bufferedWriter.write(newJsonObject.toString())
            bufferedWriter.close()
        }

        fun readDataFromJSON(context:Context): String? {
            val file = File(context.filesDir, Constant.JSON_FILE_NAME)
            if(file.exists()) {
                val fileReader = FileReader(file)
                val bufferedReader = BufferedReader(fileReader)
                val stringBuilder = StringBuilder()
                var line = bufferedReader.readLine()
                while (line != null) {
                    stringBuilder.append(line).append("\n")
                    line = bufferedReader.readLine()
                }
                bufferedReader.close()
                return stringBuilder.toString()
            }
            return null
        }

         fun addTextInputLayout(context: Context,userFormUi: UserForm): TextInputLayout {
            val editTextWithType = TextInputLayout(
                context,
                null,
                R.style.Widget_MaterialComponents_TextInputLayout_FilledBox_ExposedDropdownMenu
            )
            editTextWithType.id = View.generateViewId()
            val inputLayout = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            editTextWithType.layoutParams = inputLayout
            inputLayout.marginStart =
                context.resources.getDimensionPixelSize(com.example.hubblerassigment.R.dimen.width_10)
            inputLayout.marginEnd =
                context.resources.getDimensionPixelSize(com.example.hubblerassigment.R.dimen.width_10)
            if (userFormUi.required == "true")
                editTextWithType.hint =
                    Html.fromHtml("${userFormUi.fieldName} <font color =\"#cc0029\" >*</font>", 0)
            else {
                editTextWithType.hint = userFormUi.fieldName
                editTextWithType.boxBackgroundColor =
                    context.resources.getColor(com.example.hubblerassigment.R.color.transparent, null)
            }
            if (userFormUi.type == "dropdown") {
                val dropDown = AutoCompleteTextView(editTextWithType.context)
                dropDown.hint = userFormUi.fieldName
                val adapter =
                    ArrayAdapter(context, com.example.hubblerassigment.R.layout.list_user_form, userFormUi.options)
                adapter.setDropDownViewResource(com.example.hubblerassigment.R.layout.list_user_form)
                dropDown.setAdapter(adapter)
                dropDown.setText(adapter.getItem(0).toString(), false)
                editTextWithType.addView(dropDown)
            } else {
                val editText = TextInputEditText(editTextWithType.context)
                editTextWithType.addView(editText)
                if (userFormUi.type == "number") {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    editText.maxLines = 1
                } else if (userFormUi.type == "text") {
                    editText.maxLines = 1
                }
            }
             return editTextWithType
        }
    }
}