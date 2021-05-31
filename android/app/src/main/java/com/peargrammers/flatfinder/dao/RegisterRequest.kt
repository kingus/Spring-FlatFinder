package com.peargrammers.flatfinder.dao

data class RegisterRequest(
    var username: String,
    var email: String,
    var preffered_district: String,
    var password: String
)