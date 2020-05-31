package com.example.assignment.ui.main.dashboard

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R
import com.example.assignment.models.DetailAPIResponse
import com.example.assignment.ui.main.details.DashboardClickListener


class DashBoardDataAdapter(var list : ArrayList<DetailAPIResponse?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var listener: DashboardClickListener? = null
    private lateinit var relativeLayoutItem : RelativeLayout

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboard_fragment_item, parent, false)
        return DashBoardViewHolder(view,list)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is DashBoardViewHolder) {
            list[position]?.let { viewHolder.bindData(it) }
        }
    }

    fun setData(list : ArrayList<DetailAPIResponse?>) {
        this.list = list
        Log.d("setData","setData called")
        notifyDataSetChanged()
    }

    inner class DashBoardViewHolder(itemView: View, list: ArrayList<DetailAPIResponse?>) : RecyclerView.ViewHolder(itemView) {
        private var textViewTitle: TextView
        private var textViewSubTitle: TextView

        init {
            textViewTitle = itemView.findViewById(R.id.textViewTitle)
            textViewSubTitle = itemView.findViewById(R.id.textViewSubTitle)
            relativeLayoutItem = itemView.findViewById<RelativeLayout>(R.id.relativeLayoutItem)
            relativeLayoutItem.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

        fun bindData(detailedResponse: DetailAPIResponse) {
            textViewTitle.text = detailedResponse.title
            textViewSubTitle.text = detailedResponse.by
        }
    }
}