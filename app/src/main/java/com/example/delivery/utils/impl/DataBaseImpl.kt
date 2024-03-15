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
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import java.sql.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import kotlin.random.Random



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
    override fun update_acc(email : String) {
        CoroutineScope(Dispatchers.IO).launch {
            supabase.auth.modifyUser {
                this.email = email
            }
        }
    }

    override fun log_out() {
        CoroutineScope(Dispatchers.IO).launch {
            supabase.auth.signOut()
        }
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

    override suspend fun get_profile(email: String) : Result<Int?, String> {
        try {
            val profile = supabase.from("profiles").select() {
                filter {
                    eq("email_address", email)
                }
            }.decodeSingle<profiles>()

            return Result.Success(profile.id)

        } catch (e: Exception) {
            //Log.e("EXCEPTION", "Exception count profile: " + e.message)
            return Result.Error("Профиль не найден")
        }
    }

    override fun add_profile(full_name : String, phone_number: String, email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val profile = profiles(full_name = full_name, phone_number = phone_number, email_address = email, avatar_file = "default")
                supabase.postgrest["profiles"].insert(profile)

            } catch (e : Exception) {
                //Log.e("EXCEPTION", "Exception add profile: " + e.toString())
                Log.e("EXCEPTION", "Exception add profile: " + e.message)
            }
        }
    }

    override fun update_profile(full_name : String, phone_number: String, email: String) {
        CoroutineScope(Dispatchers.IO).launch {
            supabase.from("profiles").update(
                {
                    set("full_name", full_name)
                    set("phone_number", phone_number)
                    set("email_address", email)
                }
            )
            {
                filter {
                    eq("email_address", email)
                }
            }
        }
    }

    override suspend fun add_location(profile: Int, address: String, state_country: String, phone_number: String, others: String) : Int? {
        try {
            val location = locations(profile = profile, address = address, state_country = state_country, phone_number = phone_number, others = if (others != "") others else "")
            var new_location = supabase.from("locations").insert(location) {
                select()
            }.decodeSingle<locations>()
            return new_location.id
        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception add location: " + e.message)
            return null
        }
    }

    override suspend fun get_location(profile_id: Int, address: String) : Int? {
        try {
            val location = supabase.from("locations").select() {
                filter {
                    eq("profile", profile_id)
                    eq("address", address)
                }
            }.decodeList<locations>()

            if (location.count() == 0) {
                return null
            }
            return location[0].id

        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception get location: " + e.message)
            return null
        }
    }

    override suspend fun add_package(profile: Int, package_items: String, weight_item: String, worth_items: String, origin: Int,
                                     instant_delivery: Boolean, delivery_charges: Float, instant_delivery_charges: Float, tax_service_charges: Float,
                                     tracking_number: String) : Int? {
        try {
            val kit = packages(profile = profile, package_items = package_items, weight_item = weight_item, worth_items = worth_items,
                origin = origin, instant_delivery = instant_delivery, delivery_charges = delivery_charges,
                instant_delivery_charges = instant_delivery_charges, tax_service_charges = tax_service_charges, tracking_number = tracking_number, rider = Random.nextInt(0, 10))

            val new_package = supabase.from("packages").insert(kit) {
                select()
            }.decodeSingle<packages>()

            return new_package.id
        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception add package: " + e.message)
            return null
        }
    }

    override fun get_package() {
        TODO("Not yet implemented")
    }

    override suspend fun add_delivery(new_package: Int, destination: Int) : Int? {
        try {
            val delivery = deliveries(`package` = new_package, destination = destination)
            val new_delivery = supabase.from("deliveries").insert(delivery) {
                select()
            }.decodeSingle<deliveries>()

            return new_delivery.id
        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception add delivery: " + e.message)
            return null
        }
    }

    override suspend fun get_delivery(package_id: Int) : Int? {
        try {
            val delivery = supabase.from("deliveries").select {
                filter {
                    eq("package", package_id)
                }
            }.decodeSingle<deliveries>()

            return delivery.id
        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception get delivery: " + e.message)
            return null
        }
    }

    override suspend fun add_transaction(profile_id: Int, package_id: Int, amount: Float, comment: String) {
        try {
            val time: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val transaction = transactions(profile = profile_id, `package` = package_id, amount = amount, comment = comment, ts = time)

            supabase.from("transactions").insert(transaction)
        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception add transaction: " + e.message)
        }
    }

    override suspend fun get_transaction() {
        TODO("Not yet implemented")
    }

    override fun get_feedbacks() {
        TODO("Not yet implemented")
    }

    override fun add_feedback() {
        TODO("Not yet implemented")
    }

    override suspend fun add_routes(profile_id: Int, delivery_id: Int) {
        try {
            val route = routes(profile = profile_id, delivery = delivery_id, step = Random.nextInt(1, 4))

            supabase.from("routes").insert(route)
        } catch (e: Exception) {
            Log.e("EXCEPTION", "Exception add route: " + e.message)
        }
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
        val ts : LocalDateTime
    )
}