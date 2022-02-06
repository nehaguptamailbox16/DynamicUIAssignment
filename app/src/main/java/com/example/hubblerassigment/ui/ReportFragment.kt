package com.example.hubblerassigment.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.hubblerassigment.R
import com.example.hubblerassigment.databinding.FragmentReportBinding

class ReportFragment : Fragment() {

    private var _binding: FragmentReportBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_ReportFragment_to_UserFormFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        val userFormData = arguments?.get("userData")
        println("$userFormData")
        if(userFormData != null){
            binding.textviewNoreports.visibility= View.GONE
            binding.textviewAddreport.visibility= View.GONE
           setUserFormData(userFormData as Map<String, String>)
        }
    }

    private fun setUserFormData(userFormData: Map<String,String>) {
        var position = 1
        for((key, value) in userFormData) {
            if(position == 1) {
                binding.textviewValueone.text = value
                binding.textviewHeadingone.text = key
            }
            if(position == 2){
                binding.textviewValuetwo.text = value
                binding.textviewHeadingtwo.text = key
            }
            position++
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}