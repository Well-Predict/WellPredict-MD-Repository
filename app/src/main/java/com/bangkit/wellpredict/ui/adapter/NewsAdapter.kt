package com.bangkit.wellpredict.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bangkit.wellpredict.data.api.response.ArticlesItem
import com.bangkit.wellpredict.databinding.ItemNewsBinding
import com.bangkit.wellpredict.ui.news.NewsWebViewActivity
import com.bangkit.wellpredict.utils.DateHelper

class NewsAdapter : ListAdapter<ArticlesItem, NewsAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, NewsWebViewActivity::class.java)
            intent.putExtra("NEWS_URL", item.url)
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class MyViewHolder(private val binding: ItemNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ArticlesItem) {
            Glide.with(itemView.context)
                .load(item.urlToImage)
                .into(binding.ivNewsImage)
            binding.tvNewsAuthor.text = item.source?.name
            binding.tvNewsTitle.text = item.title
            binding.tvNewsUploadTime.text = DateHelper.convertTime(item.publishedAt.toString())
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ArticlesItem>() {
            override fun areItemsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ArticlesItem, newItem: ArticlesItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}