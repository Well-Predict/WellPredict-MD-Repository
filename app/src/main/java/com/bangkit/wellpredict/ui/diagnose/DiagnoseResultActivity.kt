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

        val symptomList = intent.getStringArrayListExtra("SYMPTOM_LIST")

        viewModel.diagnose(symptomList!!.toTypedArray()).observe(this) { result ->
            when (result) {
                is ResultState.Loading -> {
                    binding.greetingsSection.visibility = View.INVISIBLE
                    binding.diseaseResultSection.visibility = View.INVISIBLE
                    lottieLoadingAnimation(true)
                }
                is ResultState.Success -> {
                    binding.greetingsSection.visibility = View.VISIBLE
                    binding.diseaseResultSection.visibility = View.VISIBLE
                    lottieLoadingAnimation(false)
                    binding.tvDiseaseName.text = result.data.diseaseData?.result.toString()
                }
                is ResultState.Error -> {
                    binding.greetingsSection.visibility = View.INVISIBLE
                    binding.diseaseResultSection.visibility = View.INVISIBLE
                    lottieLoadingAnimation(false)
                    Toast.makeText(this, result.error.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun lottieLoadingAnimation(isLoading: Boolean){
        if (isLoading) {
            binding.lottieLoadingAnimation.playAnimation()
        } else {
            binding.lottieLoadingAnimation.setVisibility(View.GONE)
        }
    }

    fun onBackButtonClick(view: View) {
        finish()
    }
}