package com.example.projekatzavrsni.data.local.dao

import androidx.room.*
import com.example.projekatzavrsni.data.local.entity.PersonEntity

@Dao
interface PersonDao {
    @Query("SELECT * FROM persons")
    suspend fun getAllPersons(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(persons: List<PersonEntity>)

    @Query("DELETE FROM persons")
    suspend fun clearAll()

    @Query("SELECT * FROM persons WHERE is_favorite = 1")
    suspend fun getFavorites(): List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(person: PersonEntity)

}
