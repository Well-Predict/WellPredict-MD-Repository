package com.bangkit.wellpredict.ui.diagnose

import SymptomPreference
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.api.response.SymptomsItem
import com.bangkit.wellpredict.databinding.ActivityDiagnoseSearchBinding
import com.bangkit.wellpredict.ui.ViewModelFactory
import com.bangkit.wellpredict.ui.adapter.SymptomAdapter

class DiagnoseSearchActivity : AppCompatActivity() {

    private val viewModel by viewModels<DiagnoseSearchViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var binding: ActivityDiagnoseSearchBinding
    private lateinit var adapter: SymptomAdapter
    private var originalList: List<SymptomsItem?> = emptyList()
    private lateinit var symptomPreference: SymptomPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnoseSearchBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        setupRecyclerView()
        setupSearchView()

        // Observasi perubahan symptomList
        viewModel.getSymptomsList().observe(this) { result ->
            if (result != null){
                when (result) {
                    is ResultState.Loading -> {
//                        lottieLoadingAnimation(true)
                    }
                    is ResultState.Success -> {
                        originalList = result.data ?: emptyList()
                        if (binding.searchView.query.isNullOrBlank()) {
                            binding.rvSymptomList.visibility = View.GONE
                        } else {
                            binding.rvSymptomList.visibility = View.VISIBLE
                            adapter.submitList(result.data)
                        }

//                        lottieLoadingAnimation(false)
                    }
                    is ResultState.Error -> {
//                        lottieLoadingAnimation(false)
//                        showAlert(result.error,)
                    }
                }
            }
        }
        symptomPreference = SymptomPreference.getInstance(this)
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvSymptomList.layoutManager = layoutManager
        adapter = SymptomAdapter()
        binding.rvSymptomList.adapter = adapter
        binding.rvSymptomList.visibility = View.GONE // Awalnya RecyclerView tidak terlihat
    }

    private fun setupSearchView(){
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrBlank()) {
                    binding.rvSymptomList.visibility = View.GONE
                } else {
                    binding.rvSymptomList.visibility = View.VISIBLE
                    filterList(newText)
                }
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        adapter.setCurrentQuery(query)
        if (query.isNullOrBlank()) {
            adapter.submitList(originalList)
        } else {
            val filteredList = originalList.filter { it?.symptom?.contains(query, ignoreCase = true) ?: false }
            adapter.submitList(filteredList)
        }
    }

    fun onBackButtonClick(view: View) {
        finish()
    }
}