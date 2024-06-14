package com.will.capstonebangkit.data

sealed class ResultState<out R> private constructor() {
    object Loading : ResultState<Nothing>()
    data class Success<out T>(val data: T) : ResultState<T>()
    data class Error(val error: String) : ResultState<Nothing>()
}