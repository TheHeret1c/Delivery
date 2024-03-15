package com.example.delivery.utils

interface LocalRepository {

    fun getIsFirstExec(): Boolean
    fun setIsFirstExec(value: Boolean)

    fun setUserData(full_name: String, phone_number: String, email: String)

    fun getUserData() : List<String>

    fun setRememberPassword(password: String)

    fun getRememberPassword() : String

    fun setEmailForgotPassword(value: String)

    fun getEmailForgotPassword() : String

    fun setEmail(value: String)

    fun getEmail() : String
}