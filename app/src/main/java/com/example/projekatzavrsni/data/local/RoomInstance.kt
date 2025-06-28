package com.example.projekatzavrsni.data.local

import android.content.Context
import androidx.room.Room

object RoomInstance {
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}



