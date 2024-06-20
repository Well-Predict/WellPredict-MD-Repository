package com.bangkit.wellpredict.ui.history

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.wellpredict.data.api.response.HistoryItem
import com.bangkit.wellpredict.databinding.ActivityHistoryDetailBinding

@Suppress("DEPRECATION")
class HistoryDetailActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityHistoryDetailBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        val historyItems = intent.getParcelableArrayListExtra<HistoryItem>("HISTORY_ITEMS")
        if (!historyItems.isNullOrEmpty()) {
            val historyItem = historyItems.first()
            binding.apply {
                tvDiseaseName.text = historyItem.disease
                // Isi TextView lainnya sesuai kebutuhan
                // tvDiseaseDescription.text = historyItem.description
                // tvCausesDescription.text = historyItem.causes
                // tvTreatmentDescription.text = historyItem.treatment
            }
        } else {
            Toast.makeText(this, "No data received", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    fun onBackButtonClick(view: View) {
        finish()
    }
}