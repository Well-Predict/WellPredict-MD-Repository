package com.bangkit.wellpredict.ui.adapter

import SymptomPreference
import android.app.Activity
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.wellpredict.data.api.response.SymptomsItem
import com.bangkit.wellpredict.databinding.ItemSymptomBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class SymptomAdapter : ListAdapter<SymptomsItem, SymptomAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var currentQuery: String? = null

    fun setCurrentQuery(query: String?) {
        currentQuery = query?.trim()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemSymptomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class MyViewHolder(private val binding: ItemSymptomBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SymptomsItem) {
            val query = currentQuery

            if (!query.isNullOrEmpty()) {
                // Menyimpan teks asli sebelum di-highlight
                val originalText = item.symptom

                // Menerapkan highlight dan bold ke teks yang cocok dengan query
                val spannable = SpannableString(originalText)
                val startIndex = originalText.lowercase(Locale.ROOT).indexOf(query.lowercase(
                    Locale.ROOT
                ))
                if (startIndex != -1) {
                    val endIndex = startIndex + query.length
                    spannable.setSpan(StyleSpan(Typeface.BOLD), startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                    binding.tvSymptomTitle.text = spannable
                } else {
                    binding.tvSymptomTitle.text = originalText
                }
            } else {
                binding.tvSymptomTitle.text = item.symptom
            }

            // Handle click event
            binding.root.setOnClickListener {
                // Get the symptom item that was clicked
                val clickedSymptom = getItem(adapterPosition).symptom

                // Save the clicked symptom to DataStore
                val symptomPreference = SymptomPreference.getInstance(binding.root.context)
                CoroutineScope(Dispatchers.IO).launch {
                    symptomPreference.saveSelectedSymptom(clickedSymptom)
                }

                val activity = binding.root.context as? Activity
                activity?.finish()

            }
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SymptomsItem>() {
            override fun areItemsTheSame(oldItem: SymptomsItem, newItem: SymptomsItem): Boolean {
                // Check if the same item based on unique ID
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: SymptomsItem, newItem: SymptomsItem): Boolean {
                // Check if contents are exactly the same
                return oldItem == newItem
            }
        }
    }
}
