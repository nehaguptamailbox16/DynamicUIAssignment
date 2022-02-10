package com.example.hubblerassigment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hubblerassigment.R
import com.example.hubblerassigment.adapter.ReportAdapter.ReportViewHolder
import org.json.JSONArray
import org.json.JSONException

class ReportAdapter(private val userReportList: ArrayList<JSONArray>) : RecyclerView.Adapter<ReportViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_report_fragment, parent, false)
        return ReportViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bindItems(userReportList[position])
    }

    override fun getItemCount(): Int {
        return userReportList.size
    }

    class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bindItems(jsonArrayReport: JSONArray) {
               val tvHeadOne = itemView.findViewById(R.id.textview_headingone) as TextView
               val tvHeadTwo = itemView.findViewById(R.id.textview_headingtwo) as TextView
               val tvValueOne = itemView.findViewById(R.id.textview_valueone) as TextView
               val tvValueTwo = itemView.findViewById(R.id.textview_valuetwo) as TextView
                var position = 1
            for(i in 0 until jsonArrayReport.length()){
                val jsonObject = jsonArrayReport.getJSONObject(i)
                val iter: Iterator<String> = jsonObject.keys()
                while (iter.hasNext()) {
                    val key = iter.next()
                    try {
                        val value: Any = jsonObject.get(key)
                        if (position == 1) {
                            tvHeadOne.text = key
                            tvValueOne.text = value.toString()
                        }
                        if (position == 2) {
                            tvHeadTwo.text = key
                            tvValueTwo.text = value.toString()
                        }
                        position++
                    } catch (e: JSONException) {
                    }
                }
            }
        }
    }
}