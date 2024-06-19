package com.bangkit.wellpredict.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bangkit.wellpredict.MainActivity
import com.bangkit.wellpredict.R
import com.bangkit.wellpredict.data.api.response.LoginResponse
import com.bangkit.wellpredict.data.api.retrofit.AuthApiConfig
import com.bangkit.wellpredict.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity(), View.OnFocusChangeListener {

    private lateinit var binding: ActivityLoginBinding
    private var email: String = ""
    private var password: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.etLoginEmail.onFocusChangeListener = this
        binding.etLoginPassword.onFocusChangeListener = this

        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
//            email = binding.etLoginEmail.text.toString().trim()
//            password = binding.etLoginPassword.text.toString().trim()
//            if (!binding.layoutLoginEmail.isErrorEnabled && !binding.layoutLoginPassword.isErrorEnabled) {
//                if (email.isNotEmpty() && password.isNotEmpty()) {
//                    userLogin(email, password)
//                } else {
//                    binding.layoutLoginEmail.error = "Email is Required"
//                    binding.layoutLoginPassword.error = "Password is Required"
//                }
//
//            } else {
//                Toast.makeText(this@LoginActivity, "Please re-check your e-mail or password", Toast.LENGTH_LONG).show()
//            }
        }

        binding.tvRegisterRedirect.text =
            Html.fromHtml("<font>Not yet registered? </font>" + "<font color=#5AB2FF>Sign Up</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.tvRegisterRedirect.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun userLogin(email: String, password: String) {
        val client = AuthApiConfig.ApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val accessToken = response.body()?.data?.accesToken.toString()
                    val refreshToken = response.body()?.data?.refreshToken.toString()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this@LoginActivity, "Make sure your password and email are correct", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, t.message,  Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun validateEmail(): Boolean {
        var errorMessage: String? = null
        val email: String = binding.etLoginEmail.text.toString()
        if (email.isEmpty()) {
            errorMessage = "Email is Required"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            errorMessage = "Invalid Email address"
        }

        if (errorMessage != null) {
            binding.layoutLoginEmail.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    private fun validatePassword(): Boolean{
        var errorMessage: String? = null
        val password: String = binding.etLoginPassword.text.toString()
        if (password.isEmpty()) {
            errorMessage = "Password is Required"
        } else if (password.length < 8) {
            errorMessage = "Password must be 8 characters long"
        } else if (!password.any {it.isDigit()}) {
            errorMessage = "Password must be a mix between numbers and characters"
        }

        if (errorMessage != null) {
            binding.layoutLoginPassword.apply {
                isErrorEnabled = true
                error = errorMessage
            }
        }

        return errorMessage == null
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.etLoginEmail -> {
                    if (hasFocus) {
                        if (binding.layoutLoginEmail.isErrorEnabled) {
                            binding.layoutLoginEmail.isErrorEnabled = false
                        }
                    } else {
                        validateEmail()
                    }
                }
                R.id.etLoginPassword -> {
                    if (hasFocus) {
                        if (binding.layoutLoginPassword.isErrorEnabled) {
                            binding.layoutLoginPassword.isErrorEnabled = false
                        }
                    } else {
                        validatePassword()
                    }
                }
            }
        }
    }
}