package com.peargrammers.flatfinder.utils


enum class Status(val code: Int) {
    STATUS_OK(200),
    UNAUTHORIZED(401),
    INTERNAL_SERVER_ERROR(500)

}