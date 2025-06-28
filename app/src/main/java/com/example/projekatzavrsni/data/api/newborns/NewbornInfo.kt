package com.example.projekatzavrsni.data.api.newborns

data class NewbornInfo(
    val id: Int = 0,
    val institution: String,
    val maleTotal: Int,
    val femaleTotal: Int,
    val total: Int,
    val entity: String,
    val canton: String,
    val municipality: String,
    val year: Int,
    val month: Int,
    val dateUpdate: String,
    val isFavorite: Boolean = false
)
