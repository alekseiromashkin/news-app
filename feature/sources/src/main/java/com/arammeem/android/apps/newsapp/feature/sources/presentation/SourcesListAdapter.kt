package com.arammeem.android.apps.newsapp.feature.sources.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arammeem.android.apps.newsapp.feature.sources.R
import com.arammeem.android.apps.newsapp.feature.sources.model.entity.SourceEntity

internal class SourcesListAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<SourcesListAdapter.ViewHolder>() {

    private var dataSet: List<SourceEntity> = emptyList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textTitle: TextView
        val textUrl: TextView
        val textDescription: TextView

        init {
            textTitle = view.findViewById(R.id.text_title)
            textUrl = view.findViewById(R.id.text_url)
            textDescription = view.findViewById(R.id.text_description)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SourceEntity>) {
        dataSet = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_source, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val sourceEntity = dataSet[position]

        viewHolder.textTitle.text = sourceEntity.name
        viewHolder.textUrl.text = sourceEntity.url
        viewHolder.textDescription.text = sourceEntity.description

        viewHolder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(sourceEntity.id, sourceEntity.name)
        }
    }

    override fun getItemCount() = dataSet.size
}
