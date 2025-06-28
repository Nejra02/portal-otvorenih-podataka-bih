package com.example.projekatzavrsni.data.repository

import com.example.projekatzavrsni.data.api.RetrofitInstance
import com.example.projekatzavrsni.data.api.newborns.NewbornInfo
import com.example.projekatzavrsni.data.api.newborns.NewbornRequest
import com.example.projekatzavrsni.data.local.dao.NewbornDao
import com.example.projekatzavrsni.data.local.entity.NewbornEntity
import com.example.projekatzavrsni.data.mapper.toEntity
import com.example.projekatzavrsni.data.mapper.toInfo
import kotlinx.coroutines.flow.Flow

class NewbornRepository(
    private val dao: NewbornDao
) {
    suspend fun getNewborns(): List<NewbornInfo> {
        return try {
            val request = NewbornRequest()
            val response = RetrofitInstance.api.getNewborns(request)

            if (response.result.isNotEmpty()) {
                val oldFavorites = dao.getFavorites().associateBy { it.institution to it.dateUpdate }

                val entities = response.result.map {
                    val wasFav = oldFavorites[it.institution to it.dateUpdate]?.isFavorite ?: false
                    it.copy(isFavorite = wasFav).toEntity()
                }
                dao.clearAll()
                dao.insertAll(entities)
            }

            dao.getAll().map { it.toInfo() }

        } catch (e: Exception) {
            println("API NEWBORNS ERROR: ${e.message}")
            dao.getAll().map { it.toInfo() }
        }
    }

    suspend fun updateFavorite(newborn: NewbornInfo) {
        dao.upsert(newborn.toEntity())
    }

    suspend fun getFavorites(): List<NewbornInfo> {
        val favorites = dao.getFavorites()
        // println(">>> BROJ FAVORITA U BAZI (newborns): ${favorites.size}")
        return favorites.map { it.toInfo() }
    }

    suspend fun countByEntity(entity: String): Int {
        return dao.countByEntity(entity)
    }

    suspend fun getAllLocal(): List<NewbornEntity> {
        return dao.getAll()
    }



}
