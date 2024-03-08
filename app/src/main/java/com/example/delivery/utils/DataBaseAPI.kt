package com.example.delivery.utils

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

    fun get_profile()

    fun add_profile(full_name : String, phone_number: String, email: String)

    fun update_profile(full_name : String, phone_number: String, email: String)


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
}