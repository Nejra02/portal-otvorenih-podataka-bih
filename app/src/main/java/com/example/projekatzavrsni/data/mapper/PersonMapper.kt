package com.example.projekatzavrsni.data.mapper

import com.example.projekatzavrsni.data.api.persons.PersonInfo
import com.example.projekatzavrsni.data.local.entity.PersonEntity

fun PersonInfo.toEntity(): PersonEntity = PersonEntity(
    canton = canton,
    dateUpdate = dateUpdate,
    entity = entity,
    institution = institution,
    month = month,
    municipality = municipality,
    total = total,
    withResidenceTotal = withResidenceTotal,
    year = year,
    isFavorite = this.isFavorite
)

fun PersonEntity.toInfo(): PersonInfo = PersonInfo(
    canton = canton,
    dateUpdate = dateUpdate,
    entity = entity,
    institution = institution,
    month = month,
    municipality = municipality,
    total = total,
    withResidenceTotal = withResidenceTotal,
    year = year,
    isFavorite = this.isFavorite
)
