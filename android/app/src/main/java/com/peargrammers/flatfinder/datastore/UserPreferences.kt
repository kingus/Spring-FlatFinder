package com.peargrammers.flatfinder.datastore

import kotlinx.coroutines.flow.Flow

interface UserPreferences {

    val authToken: Flow<String?>

    suspend fun saveAuthToken(token: String)

}