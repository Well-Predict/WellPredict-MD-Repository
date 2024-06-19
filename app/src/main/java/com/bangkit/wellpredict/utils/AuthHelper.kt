package com.bangkit.wellpredict.utils

import android.util.Patterns
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

object AuthHelper {

    private val nameLiveData = MutableLiveData<String>()
    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val confirmPasswordLiveData = MutableLiveData<String>()

    init {
        nameLiveData.value = ""
        emailLiveData.value = ""
        passwordLiveData.value = ""
        confirmPasswordLiveData.value = ""
    }


    fun observeLoginInputChanges(button: MaterialButton) {
        emailLiveData.observeForever { email ->
            val password = passwordLiveData.value ?: ""
            updateLoginButtonStatus(button, email, password)
        }

        passwordLiveData.observeForever { password ->
            val email = emailLiveData.value ?: ""
            updateLoginButtonStatus(button, email, password)
        }
    }

    fun observeRegisterInputChanges(button: MaterialButton) {
        emailLiveData.observeForever { email ->
            val name = nameLiveData.value ?: ""
            val password = passwordLiveData.value ?: ""
            val confirmPassword = confirmPasswordLiveData.value ?: ""
            updateRegisterButtonStatus(button, name, email, password, confirmPassword)
        }

        passwordLiveData.observeForever { password ->
            val name = nameLiveData.value ?: ""
            val email = emailLiveData.value ?: ""
            val confirmPassword = confirmPasswordLiveData.value ?: ""
            updateRegisterButtonStatus(button, name, email, password, confirmPassword)
        }
        nameLiveData.observeForever { name ->
            val email = emailLiveData.value ?: ""
            val password = passwordLiveData.value ?: ""
            val confirmPassword = confirmPasswordLiveData.value ?: ""
            updateRegisterButtonStatus(button, name, email, password, confirmPassword)
        }

        confirmPasswordLiveData.observeForever { confirmPassword ->
            val name = nameLiveData.value ?: ""
            val email = emailLiveData.value ?: ""
            val password = passwordLiveData.value ?: ""
            updateRegisterButtonStatus(button, name, email, password, confirmPassword)
        }
    }

    private fun updateLoginButtonStatus(button: MaterialButton, email: String, password: String) {
        val isValidEmail = isValidEmail(email)
        val isValidPassword = isValidPassword(password)

        button.isEnabled = isValidEmail && isValidPassword
        button.alpha = if (isValidEmail && isValidPassword) 1.0f else 0.6f
    }
    private fun updateRegisterButtonStatus(
        button: MaterialButton,
        name: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val isValidName = isValidName(name)
        val isValidEmail = isValidEmail(email)
        val isValidPassword = isValidPassword(password)
        val isValidConfirmPassword = isValidConfirmPassword(confirmPassword)

        button.isEnabled = isValidEmail && isValidPassword && isValidName && isValidConfirmPassword
        button.alpha = if (isValidEmail && isValidPassword && isValidName && isValidConfirmPassword) 1.0f else 0.6f
    }

    fun isValidName(name: String): Boolean {
        return name.matches(Regex("^[a-zA-Z\\s]+$"))
    }

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 8
    }

    fun isValidConfirmPassword(confirmPassword: String): Boolean {
        return confirmPassword == passwordLiveData.toString()
    }

    @JvmStatic
    @BindingAdapter("app:validateName")
    fun validateName(editText: TextView, validate: Boolean?) {
        if (validate == true) {
            editText.doAfterTextChanged {
                val name = it.toString().trim()
                val isValid = name.matches(Regex("^[a-zA-Z\\s]+$"))
                val parent = editText.parent.parent as? TextInputLayout
                if (isValid) {
                    parent?.error = null
                } else {
                    parent?.error = "Invalid Name format"
                }
                nameLiveData.value = name
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:validateEmail")
    fun validateEmail(editText: TextView, validate: Boolean?) {
        if (validate == true) {
            editText.doAfterTextChanged {
                val email = it.toString().trim()
                val isValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                val parent = editText.parent.parent as? TextInputLayout
                if (isValid) {
                    parent?.error = null
                } else {
                    parent?.error = "Invalid email format"
                }
                emailLiveData.value = email
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:validatePassword")
    fun validatePassword(editText: TextView, validate: Boolean?) {
        if (validate == true) {
            editText.doAfterTextChanged {
                val password = it.toString().trim()
                val parent = editText.parent.parent as? TextInputLayout
                if (password.length >= 8) {
                    parent?.error = null
                } else {
                    parent?.error = "Password must be at least 8 characters long"
                }
                passwordLiveData.value = password
            }
        }
    }

    @JvmStatic
    @BindingAdapter("app:validateConfirmPassword")
    fun validateConfirmPassword(editText: TextView, validate: Boolean?) {
        if (validate == true) {
            editText.doAfterTextChanged {
                val confirmPassword = it.toString().trim()
                val parent = editText.parent.parent as? TextInputLayout
                if (confirmPassword == passwordLiveData.toString()) {
                    parent?.error = null
                } else {
                    parent?.error = "Password does not match"
                }
                confirmPasswordLiveData.value = confirmPassword
            }
        }
    }
}