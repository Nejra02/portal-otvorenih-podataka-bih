package com.example.projekatzavrsni.data.repository


import com.example.projekatzavrsni.data.api.persons.PersonInfo
import com.example.projekatzavrsni.data.api.RetrofitInstance
import com.example.projekatzavrsni.data.api.persons.PersonsRequest
import com.example.projekatzavrsni.data.local.dao.PersonDao
import com.example.projekatzavrsni.data.local.entity.PersonEntity
import com.example.projekatzavrsni.data.mapper.toEntity
import com.example.projekatzavrsni.data.mapper.toInfo


class PersonsRepository (
    private val dao: PersonDao
) {
    suspend fun getPersons(): List<PersonInfo> {
        return try {
            val response = RetrofitInstance.personsApi.getPersons(PersonsRequest())

            if (response.result.isNotEmpty()) {
                val oldFavorites = dao.getFavorites().associateBy { it.institution to it.dateUpdate }

                val entities = response.result.map {
                    val wasFav = oldFavorites[it.institution to it.dateUpdate]?.isFavorite ?: false
                    it.copy(isFavorite = wasFav).toEntity()
                }

                dao.clearAll()
                dao.insertAll(entities)

            }

            dao.getAllPersons().map { it.toInfo() }

        } catch (e: Exception) {
            println("API PERSONS ERROR: ${e.message}")
            dao.getAllPersons().map { it.toInfo() }
        }
    }

    suspend fun updateFavorite(person: PersonInfo) {
        dao.upsert(person.toEntity())
    }
    suspend fun getFavorites(): List<PersonInfo> {
        val favorites = dao.getFavorites()
        // println(">>> BROJ FAVORITA U BAZI: ${favorites.size}")
        return favorites.map { it.toInfo() }
    }

    suspend fun getAllLocalPersons(): List<PersonEntity> {
        return dao.getAllPersons()
    }

}