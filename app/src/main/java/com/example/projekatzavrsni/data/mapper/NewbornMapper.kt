package com.example.projekatzavrsni.data.mapper

import com.example.projekatzavrsni.data.api.newborns.NewbornInfo
import com.example.projekatzavrsni.data.local.entity.NewbornEntity

fun NewbornInfo.toEntity(): NewbornEntity = NewbornEntity(
    institution = institution,
    maleTotal = maleTotal,
    femaleTotal = femaleTotal,
    total = total,
    entity = entity,
    canton = canton,
    municipality = municipality,
    year = year,
    month = month,
    dateUpdate = dateUpdate,
    isFavorite = this.isFavorite
)

fun NewbornEntity.toInfo(): NewbornInfo = NewbornInfo(
    institution = institution,
    maleTotal = maleTotal,
    femaleTotal = femaleTotal,
    total = total,
    entity = entity,
    canton = canton.orEmpty(),
    municipality = municipality,
    year = year,
    month = month,
    dateUpdate = dateUpdate,
    isFavorite = this.isFavorite
)
