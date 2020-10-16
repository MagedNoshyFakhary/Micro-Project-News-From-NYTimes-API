package com.mego.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mego.myapplication.R
import com.mego.myapplication.models.News
import kotlinx.android.synthetic.main.news_item.view.*

/*Adapter For News Fragment*/

class NewsAdapter () : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

        lateinit var imageUrl:String
        inner class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

        private val differCallback = object : DiffUtil.ItemCallback<News>() {
            override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
                return oldItem == newItem
            }
        }

        val differ = AsyncListDiffer(this, differCallback)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
            return ArticleViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.news_item,
                    parent,
                    false
                )
            )
        }

        override fun getItemCount(): Int {
            return differ.currentList.size
        }

        private var onItemClickListener: ((News) -> Unit)? = null

        override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
            val article = differ.currentList[position]
            holder.itemView.apply {
               imageUrl= article.multimedia!![0].url.toString()
                Glide.with(this).load(imageUrl).into(image)
                title.text = article.title
                var strDate = article.published_date.toString()
                if (strDate.length>1) publish_date.text = strDate.substring(0,9)
                else publish_date.text = "Unknown Date"


                setOnClickListener {
                    onItemClickListener?.let { it(article) }
                }
            }
        }

        fun setOnItemClickListener(listener: (News) -> Unit) {
            onItemClickListener = listener
        }
    }