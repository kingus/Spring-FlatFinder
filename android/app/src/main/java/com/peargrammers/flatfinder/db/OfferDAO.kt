package com.peargrammers.flatfinder.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.peargrammers.flatfinder.model.UserOffer

@Dao
interface OfferDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(offer: UserOffer): Long

    @Query("SELECT * FROM user_offer")
    fun getAll(): LiveData<List<UserOffer>>

    @Delete
    suspend fun delete(offer: UserOffer)

    @Query("SELECT * FROM user_offer WHERE title LIKE :searchQuery")
    fun searchUserOffer(searchQuery: String): LiveData<List<UserOffer>>
}