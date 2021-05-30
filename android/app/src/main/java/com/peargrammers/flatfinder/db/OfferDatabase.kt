package com.peargrammers.flatfinder.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.peargrammers.flatfinder.model.UserOffer

@Database(
    entities = [UserOffer::class],
    version = 4
)
abstract class OfferDatabase : RoomDatabase() {
    abstract fun getOfferDao(): OfferDAO

    companion object {
        @Volatile
        private var instance: OfferDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                OfferDatabase::class.java, "OfferDB.db"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}