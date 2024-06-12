package com.will.capstonebangkit.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.will.capstonebangkit.R
import com.will.capstonebangkit.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity(), View.OnFocusChangeListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.etUsername.onFocusChangeListener = this
        binding.etEmail.onFocusChangeListener = this
        binding.etPassword.onFocusChangeListener = this
        binding.etConfirmPassword.onFocusChangeListener = this

        binding.tvLoginRedirect.text =
            Html.fromHtml("<font color=${Color.WHITE}>Already have an account? </font>" + "<font color=#5AB2FF>Log In</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.tvLoginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun validateUsername(): Boolean {
        var errorMessage: String? = null
        val username: String = binding.etUsername.text.toString()
        if (username.isEmpty()) {
            errorMessage = "Username is Required"
        }

        if (errorMessage != null) {
            binding.layoutUserName.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val email: String = binding.etEmail.text.toString()
        if (email.isEmpty()) {
            errorMessage = "Email is Required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorMessage = "Invalid Email address"
        }

        if (errorMessage != null) {
            binding.layoutEmail.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        val password: String = binding.etPassword.text.toString()
        if (password.isEmpty()) {
            errorMessage = "Password is Required"
        } else if (password.length < 8) {
            errorMessage = "Password must be 8 characters long"
        } else if (!password.any {it.isDigit()}) {
            errorMessage = "Password must be a mix between numbers and characters"
        }

        if (errorMessage != null) {
            binding.layoutPassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validateConfirmPassword(): Boolean{
        var errorMessage: String? = null
        val password: String = binding.etConfirmPassword.text.toString()
        if (password.isEmpty()) {
            errorMessage = "Password is Required"
        } else if (password.length < 8) {
            errorMessage = "Password must be 8 characters long"
        } else if (!password.any {it.isDigit()}) {
            errorMessage = "Password must be a mix between numbers and characters"
        }

        if (errorMessage != null) {
            binding.layoutConfirmPassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePasswordMatchedWithConfirmPassword(): Boolean {
        var errorMessage: String? = null
        val password: String = binding.etPassword.text.toString()
        val confirmPassword: String = binding.etConfirmPassword.text.toString()
        if (password != confirmPassword) {
            errorMessage = "Confirm Password doesn't match with Password"
        }

        if (errorMessage != null) {
            binding.layoutConfirmPassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.etUsername -> {
                    if (hasFocus) {
                        if (binding.layoutUserName.isErrorEnabled) {
                            binding.layoutUserName.isErrorEnabled = false
                        }
                    } else {
                        validateUsername()
                    }
                }
                R.id.etEmail -> {
                    if (hasFocus) {
                        if (binding.layoutEmail.isErrorEnabled) {
                            binding.layoutEmail.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }
                R.id.etPassword -> {
                    if (hasFocus) {
                        if (binding.layoutPassword.isErrorEnabled) {
                            binding.layoutPassword.isErrorEnabled = false
                        }
                    } else {
                        validatePassword()
                    }
                }
                R.id.etConfirmPassword -> {
                    if (hasFocus) {
                        if (binding.layoutConfirmPassword.isErrorEnabled) {
                            binding.layoutConfirmPassword.isErrorEnabled = false
                        }
                    } else {
                        validateConfirmPassword()
                    }
                }
            }
        }
    }
}