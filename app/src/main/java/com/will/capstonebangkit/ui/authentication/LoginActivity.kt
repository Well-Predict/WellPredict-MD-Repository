package com.will.capstonebangkit.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.will.capstonebangkit.MainActivity
import com.will.capstonebangkit.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.tvRegisterRedirect.text =
            Html.fromHtml("<font>Not yet registered? </font>" + "<font color=#5AB2FF>Sign Up</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)

        binding.tvRegisterRedirect.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}