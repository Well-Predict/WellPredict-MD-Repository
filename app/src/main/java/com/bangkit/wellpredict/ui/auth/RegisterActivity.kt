package com.bangkit.wellpredict.ui.auth

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.data.model.User
import com.bangkit.wellpredict.databinding.ActivityRegisterBinding
import com.bangkit.wellpredict.ui.ViewModelFactory
import com.bangkit.wellpredict.utils.AuthHelper

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        AuthHelper.observeRegisterInputChanges(binding.btnRegister)
        setupRegisterClickListener()
//        registerOnClickHandler()

    }

    private fun setupRegisterClickListener() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etNameInput.text.toString().trim()
            val email = binding.etEmailInput.text.toString().trim()
            val password = binding.etPasswordInput.text.toString().trim()
            val confirmPassword = binding.etConfirmPasswordInput.text.toString().trim()

            if (AuthHelper.isValidName(name) && AuthHelper.isValidEmail(email) && AuthHelper.isValidPassword(password) && AuthHelper.isValidConfirmPassword(confirmPassword)) {
                viewModel.register(name, email, password).observe(this) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                        }
                        is ResultState.Success -> {
                            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        }
                        is ResultState.Error -> {
                            val error = result.error.toString()
                            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}