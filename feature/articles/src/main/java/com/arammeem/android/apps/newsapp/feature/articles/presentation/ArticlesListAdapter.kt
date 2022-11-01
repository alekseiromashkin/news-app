package com.arammeem.android.apps.newsapp.feature.articles.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.arammeem.android.apps.newsapp.core.openUrl
import com.arammeem.android.apps.newsapp.feature.articles.model.entity.ArticleEntity
import com.arammeem.android.apps.newsapp.feature.articles.R
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

internal class ArticlesListAdapter
    : PagingDataAdapter<ArticleEntity, ArticlesListAdapter.ViewHolder>(
    ArticlesDiffCallBack()
) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageThumbnail: ImageView
        val textTitle: TextView
        val textAuthor: TextView
        val textDate: TextView
        val textDescription: TextView

        init {
            imageThumbnail = view.findViewById(R.id.image_thumbnail)
            textTitle = view.findViewById(R.id.text_title)
            textAuthor = view.findViewById(R.id.text_author)
            textDate = view.findViewById(R.id.text_date)
            textDescription = view.findViewById(R.id.text_description)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_article, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val articleEntity = getItem(position)

        val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
        val date = dateFormatter.format(articleEntity?.publishedAt)

        viewHolder.imageThumbnail.load(articleEntity?.urlToImage)
        viewHolder.textTitle.text = articleEntity?.title
        viewHolder.textAuthor.text = articleEntity?.author
        viewHolder.textDate.text = date
        viewHolder.textDescription.text = articleEntity?.description

        viewHolder.itemView.setOnClickListener {
            articleEntity?.url?.let { viewHolder.itemView.context.openUrl(it) }
        }
    }
}

private class ArticlesDiffCallBack : DiffUtil.ItemCallback<ArticleEntity>() {
    override fun areItemsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ArticleEntity, newItem: ArticleEntity): Boolean {
        return oldItem == newItem
    }
}
