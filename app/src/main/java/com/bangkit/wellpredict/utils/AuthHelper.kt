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
        emailLiveData.observeForever {
            updateLoginButtonStatus(button)
        }

        passwordLiveData.observeForever {
            updateLoginButtonStatus(button)
        }
    }

    fun observeRegisterInputChanges(button: MaterialButton) {
        emailLiveData.observeForever {
            updateRegisterButtonStatus(button)
        }

        passwordLiveData.observeForever {
            updateRegisterButtonStatus(button)
        }

        nameLiveData.observeForever {
            updateRegisterButtonStatus(button)
        }

        confirmPasswordLiveData.observeForever {
            updateRegisterButtonStatus(button)
        }
    }

    private fun updateLoginButtonStatus(button: MaterialButton) {
        val email = emailLiveData.value ?: ""
        val password = passwordLiveData.value ?: ""
        val isValidEmail = isValidEmail(email)
        val isValidPassword = isValidPassword(password)

        updateButtonState(button, isValidEmail && isValidPassword)
    }

    private fun updateRegisterButtonStatus(button: MaterialButton) {
        val name = nameLiveData.value ?: ""
        val email = emailLiveData.value ?: ""
        val password = passwordLiveData.value ?: ""
        val confirmPassword = confirmPasswordLiveData.value ?: ""

        val isValidName = isValidName(name)
        val isValidEmail = isValidEmail(email)
        val isValidPassword = isValidPassword(password)
        val isValidConfirmPassword = isValidConfirmPassword(confirmPassword)

        updateButtonState(
            button,
            isValidEmail && isValidPassword && isValidName && isValidConfirmPassword
        )
    }

    private fun updateButtonState(button: MaterialButton, isValid: Boolean) {
        button.isEnabled = isValid
        button.alpha = if (isValid) 1.0f else 0.6f
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
        return confirmPassword == passwordLiveData.value
    }

    @JvmStatic
    @BindingAdapter("app:validateName")
    fun validateName(editText: TextView, validate: Boolean?) {
        if (validate == true) {
            editText.doAfterTextChanged {
                val name = it.toString().trim()
                val isValid = isValidName(name)
                updateInputError(editText, isValid, "Invalid Name format")
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
                val isValid = isValidEmail(email)
                updateInputError(editText, isValid, "Invalid email format")
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
                val isValid = isValidPassword(password)
                updateInputError(editText, isValid, "Password must be at least 8 characters long")
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
                val isValid = isValidConfirmPassword(confirmPassword)
                updateInputError(editText, isValid, "Password does not match")
                confirmPasswordLiveData.value = confirmPassword
            }
        }
    }

    private fun updateInputError(editText: TextView, isValid: Boolean, errorMessage: String) {
        val parent = editText.parent.parent as? TextInputLayout
        if (isValid) {
            parent?.error = null
        } else {
            parent?.error = errorMessage
        }
    }
}