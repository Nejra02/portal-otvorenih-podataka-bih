package com.example.projekatzavrsni.data.api.persons

data class PersonInfo (
    val id: Int = 0,
    val canton: String?,
    val dateUpdate: String,
    val entity: String,
    val institution: String,
    val month: Int,
    val municipality: String,
    val total: Int,
    val withResidenceTotal: Int,
    val year: Int,
    val isFavorite: Boolean = false
)