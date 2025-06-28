package com.example.projekatzavrsni.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projekatzavrsni.data.local.dao.PersonDao
import com.example.projekatzavrsni.data.local.entity.PersonEntity
import com.example.projekatzavrsni.data.local.dao.NewbornDao
import com.example.projekatzavrsni.data.local.entity.NewbornEntity

@Database(entities = [PersonEntity::class, NewbornEntity::class], version = 20)
abstract class AppDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun newbornDao(): NewbornDao
}

