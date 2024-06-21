package com.bangkit.wellpredict.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.wellpredict.data.ResultState
import com.bangkit.wellpredict.databinding.ActivityRegisterBinding
import com.bangkit.wellpredict.ui.ViewModelFactory
import com.bangkit.wellpredict.utils.AuthHelper
import com.bangkit.wellpredict.utils.DialogHelper
import com.saadahmedev.popupdialog.PopupDialog

class RegisterActivity : AppCompatActivity() {

    private val viewModel by viewModels<RegisterViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private var loadingDialog: PopupDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        AuthHelper.observeRegisterInputChanges(binding.btnRegister)
        setupRegisterClickListener()
        loginOnClickHandler()
    }

    private fun setupRegisterClickListener() {
        binding.btnRegister.setOnClickListener {
            val name = binding.etNameInput.text.toString().trim()
            val email = binding.etEmailInput.text.toString().trim()
            val password = binding.etPasswordInput.text.toString().trim()
            val confirmPassword = binding.etConfirmPasswordInput.text.toString().trim()

            if (AuthHelper.isValidName(name) && AuthHelper.isValidEmail(email) && AuthHelper.isValidPassword(
                    password
                ) && AuthHelper.isValidConfirmPassword(confirmPassword)
            ) {
                viewModel.register(name, email, password).observe(this) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            loadingDialog = DialogHelper.loadingDialog(this)
                            loadingDialog?.show() // Tampilkan dialog
                        }

                        is ResultState.Success -> {
                            loadingDialog?.dismiss()
                            loadingDialog = null
                            DialogHelper.successDialog(
                                this,
                                "Success",
                                "You have successfully registered " + "Login To Continue",
                                "Log In"
                            )
                        }

                        is ResultState.Error -> {
                            loadingDialog?.dismiss()
                            loadingDialog = null
                            DialogHelper.errorDialog(this, result.error)
                        }
                    }
                }
            }
        }
    }
    private fun loginOnClickHandler() {
        binding.tvLogin.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}