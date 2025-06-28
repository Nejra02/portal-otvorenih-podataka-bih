package com.example.projekatzavrsni.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "newborns", primaryKeys = ["institution", "dateUpdate"])
data class NewbornEntity(
    val institution: String,
    val maleTotal: Int,
    val femaleTotal: Int,
    val total: Int,
    val entity: String,
    val canton: String?,
    val municipality: String,
    val year: Int,
    val month: Int,
    val dateUpdate: String,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
)
