package com.example.projekatzavrsni.data.api.newborns

data class NewbornRequest(
    val updateDate: String = "2025-06-03",
    val entityId: Int = 0,
    val cantonId: Int = 0,
    val municipalityId: Int = 0,
    val year: String = "2025",
    val month: String = "5"
)
