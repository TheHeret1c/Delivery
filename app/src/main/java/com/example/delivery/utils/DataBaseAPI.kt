package com.example.delivery.utils

import com.example.delivery.utils.impl.Result

interface DataBaseAPI {

    fun getProfiles()


    // Auth

    fun auth(email : String, password : String)

    fun register(email : String, password: String)

    fun verify_code(email : String, code : String)

    fun update_acc(email : String)

    fun log_out()

    fun forgot_pass(email : String)

    fun change_pass(password: String)


    // Profiles

    suspend fun get_profile(email: String) : Result<Int?, String>

    fun add_profile(full_name : String, phone_number: String, email: String)

    fun update_profile(full_name : String, phone_number: String, email: String)


    // Location

    suspend fun add_location(profile: Int, address: String, state_country: String, phone_number: String, others: String) : Int?

    suspend fun get_location(profile_id: Int, address: String) : Int?


    // Packages

    suspend fun add_package(profile: Int, package_items: String, weight_item: String, worth_items: String, origin: Int,
                            instant_delivery: Boolean, delivery_charges: Float, instant_delivery_charges: Float, tax_service_charges: Float,
                            tracking_number: String) : Int?

    fun get_package()


    // Deliveries

    suspend fun add_delivery(new_package: Int, destination: Int) : Int?

    suspend fun get_delivery(package_id: Int) : Int?


    // Transactions
    suspend fun add_transaction(profile_id: Int, package_id: Int, amount: Float, comment: String)

    suspend fun get_transaction()


    // Feedbacks

    fun get_feedbacks()

    fun add_feedback()


    // Routes

    suspend fun add_routes(profile_id: Int, delivery_id: Int)

    fun get_routes()


    // Schedules

    fun get_schedules()


    // Map

    fun get_routing()

    fun get_geocoder()

    fun get_tiles()
}