package com.bangkit.wellpredict.ui.authentication

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bangkit.wellpredict.R
import com.bangkit.wellpredict.data.api.response.RegisterResponse
import com.bangkit.wellpredict.data.api.retrofit.AuthApiConfig
import com.bangkit.wellpredict.databinding.ActivityRegisterBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity(), View.OnFocusChangeListener {

    private lateinit var binding: ActivityRegisterBinding
    private var email: String = ""
    private var name: String = ""
    private var password: String = ""
    private var confirmPassword: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.etUsername.onFocusChangeListener = this
        binding.etEmail.onFocusChangeListener = this
        binding.etPassword.onFocusChangeListener = this
        binding.etConfirmPassword.onFocusChangeListener = this

        // register
        binding.btnRegister.setOnClickListener {
            email = binding.etEmail.text.toString().trim()
            name = binding.etUsername.text.toString().trim()
            password = binding.etPassword.text.toString().trim()
            confirmPassword = binding.etConfirmPassword.text.toString().trim()
            if (email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()){
                if (!binding.layoutUserName.isErrorEnabled && !binding.layoutEmail.isErrorEnabled && !binding.layoutPassword.isErrorEnabled && !binding.layoutConfirmPassword.isErrorEnabled) {
                    registerUser(email, name, password)
                } else {
                    Toast.makeText(this@RegisterActivity, "Make sure all of the errors are gone before continue", Toast.LENGTH_LONG).show()
                }
            } else {
                binding.layoutUserName.error = "Username is Required"
                binding.layoutEmail.error = "Email is Required"
                binding.layoutPassword.error = "Password is Required"
                binding.layoutConfirmPassword.error = "Confirm Password is Required"
            }

        }

        binding.tvLoginRedirect.text =
            Html.fromHtml("<font color=${Color.WHITE}>Already have an account? </font>" + "<font color=#5AB2FF>Log In</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.tvLoginRedirect.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun registerUser(email: String, name: String, password: String) {
        val client = AuthApiConfig.ApiService().registerUser(email, name, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                Toast.makeText(this@RegisterActivity, response.body()?.data?.message.toString(), Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, t.message, Toast.LENGTH_SHORT).show()
            }

        })
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
            errorMessage = "Confirm Password is Required"
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
                        if (validatePassword() && binding.etConfirmPassword.text!!.isNotEmpty() && validateConfirmPassword() && validatePasswordMatchedWithConfirmPassword()) {
                            if (binding.layoutConfirmPassword.isErrorEnabled) {
                                binding.layoutConfirmPassword.isErrorEnabled = false
                            }
                        }
                    }
                }
                R.id.etConfirmPassword -> {
                    if (hasFocus) {
                        if (binding.layoutConfirmPassword.isErrorEnabled) {
                            binding.layoutConfirmPassword.isErrorEnabled = false
                        }
                    } else {
                        if (validateConfirmPassword() && validatePasswordMatchedWithConfirmPassword() && validatePassword()) {
                            if (binding.layoutPassword.isErrorEnabled) {
                                binding.layoutPassword.isErrorEnabled = false
                            }
                        }
                    }
                }
            }
        }
    }
}