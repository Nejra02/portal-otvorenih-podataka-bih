package com.example.projekatzavrsni.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "persons", primaryKeys = ["institution", "dateUpdate"])
data class PersonEntity(
    val canton: String?,
    val dateUpdate: String,
    val entity: String,
    val institution: String,
    val month: Int,
    val municipality: String,
    val total: Int,
    val withResidenceTotal: Int,
    val year: Int,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
