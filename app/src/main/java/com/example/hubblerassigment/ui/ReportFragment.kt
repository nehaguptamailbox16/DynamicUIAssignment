package com.example.hubblerassigment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hubblerassigment.R
import com.example.hubblerassigment.adapter.ReportAdapter
import com.example.hubblerassigment.databinding.FragmentReportBinding
import com.example.hubblerassigment.utils.Utils
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

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
        getArgumentsFromReport()

    }

    private fun getArgumentsFromReport(){
        if(Utils.readDataFromJSON(requireContext())!=null) {
            val jsonObjectUser = JSONObject(Utils.readDataFromJSON(requireContext()))

            if (jsonObjectUser.keys().hasNext()) {
                binding.textviewNoreports.visibility = View.GONE
                binding.textviewAddreport.visibility = View.GONE
                binding.recyclerViewReport.visibility = View.VISIBLE

                val arrayListUser = ArrayList<JSONArray>()

                val iter: Iterator<String> = jsonObjectUser.keys()
                while (iter.hasNext()) {
                    val key = iter.next()
                    arrayListUser.add(jsonObjectUser.get(key) as JSONArray)
                }
                setAdapter(arrayListUser)
            }
        }
    }

    private fun setAdapter(arrList: ArrayList<JSONArray>) {
        val recyclerView = binding.recyclerViewReport
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = ReportAdapter(arrList)
        recyclerView.adapter = adapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}