package com.example.delivery.utils.impl

import android.provider.ContactsContract.Profile
import android.util.Log
import com.example.delivery.utils.DataBaseAPI
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.OtpType
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.serialization.Serializable
import java.sql.Timestamp

class DataBaseImpl : DataBaseAPI {

    companion object {
        private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRod3p4a2FqY3Fscmt2dG9xaXVrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDg5MzQ0OTEsImV4cCI6MjAyNDUxMDQ5MX0.8k6uBTbNsW-GBmFvacD_8_P1m4Z1F4Q7RKZzteMrz-w"

        private val supabase = createSupabaseClient(
            supabaseUrl = "https://thwzxkajcqlrkvtoqiuk.supabase.co",
            supabaseKey = API_KEY
        ) {
            install(Postgrest)
            install(Auth)
        }

    }

    override fun getProfiles() {
//        CoroutineScope(Dispatchers.IO).launch {
//            val client = supabase
//            //val supabaseResponse = client.postgrest["feedbacks"].select()
//            //val data = supabaseResponse.decodeList<feedbacks>()
//            //Log.e("supabase", data.toString())
//        }
        Log.e("TEST", "Message")
    }

    // Авторизация
    override fun auth(email: String, password: String) {
        CoroutineScope(Dispatchers.Default).launch {
            supabase.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }

            supabase.auth.sessionStatus.collect {
                when(it) {
                    is SessionStatus.Authenticated -> println(it.session.user)
                    SessionStatus.LoadingFromStorage -> println("Loading from storage")
                    SessionStatus.NetworkError -> println("Network error")
                    SessionStatus.NotAuthenticated -> println("Not authenticated")
                }
            }
        }
    }

    // Регистрация
    override fun register(email: String, password: String) {
        CoroutineScope(Dispatchers.Default).launch {
            supabase.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    // Код-подтверждение
    override fun verify_code(email : String, code : String) {
        CoroutineScope(Dispatchers.IO).launch {
            supabase.auth.verifyEmailOtp(
                OtpType.Email.EMAIL,
                email,
                code
            )
        }
    }

    // Смена почты
    override suspend fun update_acc(email : String) {
        supabase.auth.modifyUser {
            this.email = email
        }
    }

    override suspend fun log_out() {
        supabase.auth.signOut()
    }

    override fun forgot_pass(email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            supabase.auth.resetPasswordForEmail(
                email
            )
        }
    }

    override fun change_pass(password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            supabase.auth.modifyUser {
                this.password = password
            }
        }
    }

    override fun get_profile() {
        TODO("Not yet implemented")
    }

    override fun add_profile(full_name : String, phone_number: String, email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val profile = profiles(full_name = full_name, phone_number = phone_number, email_address = email, avatar_file = "default")
            supabase.postgrest["profiles"].insert(profile)
        }
    }

    override fun update_profile() {
        TODO("Not yet implemented")
    }

    override fun add_location() {
        TODO("Not yet implemented")
    }

    override fun get_location() {
        TODO("Not yet implemented")
    }

    override fun add_package() {
        TODO("Not yet implemented")
    }

    override fun get_package() {
        TODO("Not yet implemented")
    }

    override fun add_delivery() {
        TODO("Not yet implemented")
    }

    override fun get_deliveries() {
        TODO("Not yet implemented")
    }

    override fun get_feedbacks() {
        TODO("Not yet implemented")
    }

    override fun add_feedback() {
        TODO("Not yet implemented")
    }

    override fun get_routes() {
        TODO("Not yet implemented")
    }

    override fun get_schedules() {
        TODO("Not yet implemented")
    }

    override fun get_routing() {
        TODO("Not yet implemented")
    }

    override fun get_geocoder() {
        TODO("Not yet implemented")
    }

    override fun get_tiles() {
        TODO("Not yet implemented")
    }


    // Data classes
    @Serializable
    data class profiles (
        val id : Int = 0,
        val full_name : String = "",
        val phone_number : String = "",
        val email_address : String = "",
        val avatar_file : String = ""
    )

    @Serializable
    data class feedbacks (
        val id : Int = 0,
        val profile : Int = 0,
        val rider : Int = 0,
        val rata : Int = 0,
        val feedback : String = ""
    )

    @Serializable
    data class locations (
        val id : Int = 0,
        val profile : Int = 0,
        val address : String = "",
        val state_country : String = "",
        val phone_number : String = "",
        val others : String = "",
        val lat : Float = 0.0f,
        val lon : Float = 0.0f
    )

    @Serializable
    data class packages (
        val id : Int = 0,
        val profile : Int = 0,
        val package_items : String = "",
        val weight_item : String = "",
        val worth_items : String = "",
        val origin : Int = 0,
        val instant_delivery : Boolean = false,
        val tracking_number : String = "",
        val delivery_charges : Float = 0.0f,
        val instant_delivery_charges : Float = 0.0f,
        val tax_service_charges : Float = 0.0f,
        val rider: Int = 0
    )

    // ToDo...
    @Serializable
    data class schedules (
        val id : Int = 0,
        val profile : Int = 0,
        val `package` : Int = 0,
        val status : String = "",
        val ts : DateTimeUnit.DateBased
    )

    @Serializable
    data class routes (
        val id : Int = 0,
        val profile : Int = 0,
        val delivery : Int = 0,
        val step : Int = 0,
        val lat : Float = 0.0f,
        val lon : Float = 0.0f
    )

    @Serializable
    data class deliveries (
        val id : Int = 0,
        val profile : Int = 0,
        val `package` : Int = 0,
        val destination : Int = 0
    )

    // ToDo...
    @Serializable
    data class transactions (
        val id : Int = 0,
        val profile : Int = 0,
        val `package` : Int = 0,
        val amount : Float = 0.0f,
        val comment : String = "",
        val ts : DateTimeUnit.DateBased
    )
}