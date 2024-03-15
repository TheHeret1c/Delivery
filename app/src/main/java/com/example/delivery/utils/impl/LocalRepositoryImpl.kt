package com.example.delivery.utils.impl

import android.content.Context
import android.content.SharedPreferences
import com.example.delivery.utils.LocalRepository

class LocalRepositoryImpl(
    private val context: Context
): LocalRepository {
    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(APP_DATA, Context.MODE_PRIVATE)
    }

    override fun getIsFirstExec() = sharedPreferences.getBoolean(IS_FIRST_LAUNCH_KEY, true)

    override fun setIsFirstExec(value: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IS_FIRST_LAUNCH_KEY, value)
            .apply()
    }

    override fun setUserData(full_name: String, phone_number: String, email: String) {
        sharedPreferences.edit()
            .putString(FULL_NAME_KEY, full_name)
            .putString(PHONE_NUMBER_KEY, phone_number)
            .putString(EMAIL_KEY, email)
            .apply()
    }

    override fun getUserData(): List<String> {
        val list = mutableListOf<String>(
            sharedPreferences.getString(FULL_NAME_KEY, "").toString(),
            sharedPreferences.getString(PHONE_NUMBER_KEY, "").toString(),
            sharedPreferences.getString(EMAIL_KEY, "").toString()
        )
        return list
    }

    override fun setRememberPassword(password: String) {
        sharedPreferences.edit()
            .putString(REMEMBER_PASSWORD_KEY, password)
            .apply()
    }

    override fun getRememberPassword() : String = sharedPreferences.getString(REMEMBER_PASSWORD_KEY, "").toString()
    override fun setEmailForgotPassword(value: String) {
        sharedPreferences.edit()
            .putString(EMAIL_FORGOT_PASSWORD, value)
            .apply()
    }

    override fun getEmailForgotPassword(): String {
        return sharedPreferences.getString(EMAIL_FORGOT_PASSWORD, "").toString()
    }

    override fun setEmail(value: String) {
        sharedPreferences.edit()
            .putString(EMAIL_AUTH_KEY, value)
            .apply()
    }

    override fun getEmail(): String {
        return sharedPreferences.getString(EMAIL_AUTH_KEY, "").toString()
    }

    companion object {
        private const val APP_DATA = "APP_DATA"

        private const val IS_FIRST_LAUNCH_KEY = "IS_FIRST_LAUNCH_KEY"
        private const val FULL_NAME_KEY = "full_name"
        private const val PHONE_NUMBER_KEY = "phone_number"
        private const val EMAIL_KEY = "email"

        private const val REMEMBER_PASSWORD_KEY = "password"

        private const val EMAIL_FORGOT_PASSWORD = "email_forgot_password"

        private const val EMAIL_AUTH_KEY = "email_auth"
    }
}