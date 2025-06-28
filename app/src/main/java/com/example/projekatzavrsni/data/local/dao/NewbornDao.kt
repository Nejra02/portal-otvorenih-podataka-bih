package com.example.projekatzavrsni.data.local.dao

import androidx.room.*
import com.example.projekatzavrsni.data.local.entity.NewbornEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewbornDao {
    @Query("SELECT * FROM newborns")
    suspend fun getAll(): List<NewbornEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newborns: List<NewbornEntity>)

    @Query("DELETE FROM newborns")
    suspend fun clearAll()

    @Query("SELECT * FROM newborns WHERE is_favorite = 1")
    suspend fun getFavorites(): List<NewbornEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(person: NewbornEntity)

    @Query("SELECT COUNT(*) FROM newborns WHERE entity = :entitet")
    suspend fun countByEntity(entitet: String): Int


}
