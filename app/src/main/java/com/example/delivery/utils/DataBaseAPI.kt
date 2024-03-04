package com.example.delivery.utils

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface DataBaseAPI {

    fun getProfiles()


    // Auth

    fun auth(email : String, password : String)

    fun register(email : String, password: String)

    fun verify_code(email : String, code : String)

    suspend fun update_acc(email : String)

    suspend fun log_out()

    fun forgot_pass(email : String)

    fun change_pass(password: String)


    // Profiles

    fun get_profile()

    fun add_profile(full_name : String, phone_number: String, email: String)

    fun update_profile()


    // Location

    fun add_location()

    fun get_location()


    // Packages

    fun add_package()

    fun get_package()


    // Deliveries

    fun add_delivery()

    fun get_deliveries()


    // Feedbacks

    fun get_feedbacks()

    fun add_feedback()


    // Routes

    fun get_routes()


    // Schedules

    fun get_schedules()


    // Map

    fun get_routing()

    fun get_geocoder()

    fun get_tiles()

    companion object {

        private const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRod3p4a2FqY3Fscmt2dG9xaXVrIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MDg5MzQ0OTEsImV4cCI6MjAyNDUxMDQ5MX0.8k6uBTbNsW-GBmFvacD_8_P1m4Z1F4Q7RKZzteMrz-w"
    }
}