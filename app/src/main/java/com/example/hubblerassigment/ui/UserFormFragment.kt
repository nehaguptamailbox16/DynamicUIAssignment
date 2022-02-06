package com.example.hubblerassigment.ui

import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.*
import androidx.fragment.app.Fragment
import android.view.View.NO_ID
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.IdRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hubblerassigment.R
import com.example.hubblerassigment.databinding.FragmentUserFormBinding
import com.example.hubblerassigment.model.UserForm
import com.example.hubblerassigment.viewmodel.UserFormViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject
import kotlin.collections.LinkedHashMap

class UserFormFragment : Fragment() {

    private   var _binding: FragmentUserFormBinding? = null
    private lateinit var viewModel : UserFormViewModel
    @IdRes
    private var lastViewId = NO_ID
    private val binding get() = _binding!!
    private  var finalUserEntry = LinkedHashMap<TextInputLayout,UserForm>()
    private var isAllFieldValid= true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserFormBinding.inflate(inflater, container, false)
        viewModel =ViewModelProvider(this).get(UserFormViewModel::class.java)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivDone.setOnClickListener {
            doValidation(finalUserEntry)
        }
        getUserForm()
    }

    private fun doValidation(finalUserEntry: LinkedHashMap<TextInputLayout, UserForm>) {
        isAllFieldValid = true
        for((key, value) in finalUserEntry){
            if(value.required == "true") {
                if (!viewModel.checkRequiredField(key.editText!!.text.toString())) {
                    key.error = "Please enter value"
                    isAllFieldValid = false
                }
            }
            if(value.fieldName == "age" ){
               val age= key.editText!!.text.toString()
                if(age!="" && value.min > 0 && value.max >0) {
                    if (!viewModel.validateAge(
                            value.min,
                            value.max,
                            key.editText!!.text.toString()
                        )
                    ) {
                        key.error = "Age should be in ${value.min} and ${value.max}"
                        isAllFieldValid = false
                    }
                }
            }
        }

        if(isAllFieldValid){
            val finalJson = LinkedHashMap<String, String>()

            for((key, value) in finalUserEntry){
                finalJson.put(value.fieldName, key.editText!!.text.toString())
            }
            println("Final JSON ${JSONObject(finalJson as Map<*, *>)}")
            val bundle = bundleOf("userData" to finalJson)
            findNavController().navigate(R.id.action_UserFormFragment_to_ReportFragment, bundle)

        }
    }

    private fun getUserForm(){
        viewModel.dynamicUserForm().observe(viewLifecycleOwner) { userFormData ->
            if (userFormData != null) {
                setUi(userFormData)
            }
        }
    }

    private fun setUi(userFormData: List<UserForm>) {
        val constraintSet = ConstraintSet()


        for((indices, userFormForUi) in userFormData.withIndex()){
            if(indices == 0) {
                             val textInputLayout = addTextInputLayout(userFormForUi)
                             constraintSet.clone(binding.userFormConstraintLayout)
                             constraintSet.connect(
                                 textInputLayout.id,
                                 ConstraintSet.TOP,
                                 ConstraintSet.PARENT_ID,
                                 ConstraintSet.TOP,
                                 70
                             )
                             constraintSet.applyTo(binding.userFormConstraintLayout)
                            lastViewId = textInputLayout.id


                finalUserEntry[textInputLayout] = userFormForUi

                         }else{
                             val textInputLayout = addTextInputLayout(userFormForUi)
                             constraintSet.clone(binding.userFormConstraintLayout)
                             constraintSet.connect(
                                 textInputLayout.id,
                                 ConstraintSet.TOP,
                                 lastViewId,
                                 ConstraintSet.BOTTOM,
                                 70
                             )
                             constraintSet.applyTo(binding.userFormConstraintLayout)
                             lastViewId = textInputLayout.id
                finalUserEntry[textInputLayout] = userFormForUi
                         }
        }

    }

    private fun addTextInputLayout(userFormUi: UserForm): TextInputLayout {
        val editTextWithType = TextInputLayout(requireContext(), null, com.google.android.material.R.style.Widget_MaterialComponents_TextInputLayout_FilledBox_ExposedDropdownMenu)
        editTextWithType.id = View.generateViewId()
        val inputLayout = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        editTextWithType.layoutParams = inputLayout
        inputLayout.marginStart = requireContext().resources.getDimensionPixelSize(R.dimen.width_10)
        inputLayout.marginEnd= requireContext().resources.getDimensionPixelSize(R.dimen.width_10)
        if(userFormUi.required == "true")
        editTextWithType.hint = Html.fromHtml("${userFormUi.fieldName} <font color =\"#cc0029\" >*</font>", 0)
        else {
            editTextWithType.hint = userFormUi.fieldName
            editTextWithType.boxBackgroundColor =
                requireContext().resources.getColor(R.color.transparent, null)
        }
        if(userFormUi.type =="dropdown"){
            val dropDown = AutoCompleteTextView(editTextWithType.context)
            dropDown.hint = userFormUi.fieldName
            val adapter = ArrayAdapter(requireContext(), R.layout.list_user_form, userFormUi.options)
            adapter.setDropDownViewResource(R.layout.list_user_form)
            dropDown.setAdapter(adapter)
            dropDown.setText(adapter.getItem(0).toString(), false)
            editTextWithType.addView(dropDown)
        }else {
            val editText = TextInputEditText(editTextWithType.context)
            editTextWithType.addView(editText)
            if (userFormUi.type  == "number") {
                editText.inputType = InputType.TYPE_CLASS_NUMBER
                editText.maxLines = 1
            } else if (userFormUi.type  == "text") {
                editText.maxLines = 1
            }
        }
        binding.userFormConstraintLayout.addView(editTextWithType)
        return editTextWithType
    }


}