package com.bangkit.wellpredict.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.wellpredict.databinding.ItemSelectedSymptomBinding

class SelectedSymptomAdapter(private val onRemoveSymptom: (String) -> Unit): ListAdapter<String, SelectedSymptomAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSelectedSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val symptom = getItem(position)
        holder.bind(symptom)
    }

    inner class MyViewHolder(private val binding: ItemSelectedSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(symptom: String) {
            binding.tvSymptomTitle.text = symptom
            binding.tvSymptomTitle.setOnCloseIconClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val removeSymptom = getItem(position)
                    onRemoveSymptom(removeSymptom)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }
}