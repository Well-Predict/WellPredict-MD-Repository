package com.bangkit.wellpredict.ui.diagnose

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.databinding.ActivityDiagnoseResultBinding
import com.bangkit.wellpredict.ui.ViewModelFactory

class DiagnoseResultActivity : AppCompatActivity() {

    private val viewModel by viewModels<DiagnoseResultViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private val binding by lazy {
        ActivityDiagnoseResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel.getSession().observe(this) { user ->
            binding.tvUserName.text = user.name
        }

        val symptomList = intent.getStringArrayListExtra("SYMPTOM_LIST")
        symptomList?.let {
            observeDiagnoseResult(it.toTypedArray())
        }

    }

    private fun observeDiagnoseResult(symptomList: Array<String>) {
        viewModel.diagnose(symptomList).observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.apply {
                        setAllSectionsVisibility(false)
                        lottieLoadingAnimation(true)
                    }
                }

                is ResultState.Success -> {
                    binding.apply {
                        setAllSectionsVisibility(true)
                        lottieLoadingAnimation(false)
                        result.data?.let {
                            tvDiseaseName.text = it.disease
                            tvDiseaseDescription.text = it.description
                            tvCausesDescription.text = it.causes
                            tvTreatmentDescription.text = it.treatment
                        }
                    }
                }

                is ResultState.Error -> {
                    binding.apply {
                        setAllSectionsVisibility(false)
                        lottieLoadingAnimation(false)
                    }
                    showToast(result.error)
                }
            }
        }
    }

    private fun setAllSectionsVisibility(isVisible: Boolean) {
        val visibility = if (isVisible) View.VISIBLE else View.INVISIBLE
        binding.apply {
            greetingsSection.visibility = visibility
            diseaseSection.visibility = visibility
            causesTreatmentSection.visibility = visibility
        }
    }


    private fun lottieLoadingAnimation(isLoading: Boolean) {
        binding.lottieLoadingAnimation.apply {
            if (isLoading) {
                visibility = View.VISIBLE
                playAnimation()
            } else {
                visibility = View.GONE
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    @Suppress("UNUSED_PARAMETER")
    fun onBackButtonClick(view: View) {
        finish()
    }
}