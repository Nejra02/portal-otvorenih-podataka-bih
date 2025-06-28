package com.example.projekatzavrsni.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekatzavrsni.data.api.newborns.NewbornInfo
import com.example.projekatzavrsni.data.local.RoomInstance
import com.example.projekatzavrsni.data.local.entity.NewbornEntity
import com.example.projekatzavrsni.data.mapper.toInfo
import com.example.projekatzavrsni.data.repository.NewbornRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.stateIn


class NewbornViewModel : ViewModel() {
    lateinit var repository: NewbornRepository

    fun initRepository(context: Context) {
        val db = RoomInstance.getDatabase(context)
        val dao = db.newbornDao()
        repository = NewbornRepository(dao)
    }

    private val _newborns = MutableStateFlow<List<NewbornInfo>>(emptyList())
    val newborns: StateFlow<List<NewbornInfo>> = _newborns

    private val _filtered = MutableStateFlow<List<NewbornInfo>>(emptyList())
    val filtered: StateFlow<List<NewbornInfo>> = _filtered

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _cantonQuery = MutableStateFlow("")
    val cantonQuery: StateFlow<String> = _cantonQuery

    private val _sortOption = MutableStateFlow("Ukupno")
    val sortOption: StateFlow<String> = _sortOption

    fun getEntityCounts(): Map<String, Int> {
        return newborns.value.groupingBy { it.entity }.eachCount()
    }

    fun fetchNewborns() {
        viewModelScope.launch {
            try {
                val data = repository.getNewborns()
                _newborns.value = data
                applyFilterAndSort()
            } catch (e: Exception) {
                _error.value = e.message ?: "Nepoznata greška"
            }
        }
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        applyFilterAndSort()
    }

    fun updateCantonQuery(query: String) {
        _cantonQuery.value = query
        applyFilterAndSort()
    }

    fun updateSortOption(option: String) {
        _sortOption.value = option
        applyFilterAndSort()
    }

    private fun applyFilterAndSort() {
        val nameQ = _searchQuery.value.lowercase()
        val cantonQ = _cantonQuery.value.lowercase()

        val filtered = _newborns.value.filter {
            it.municipality.orEmpty().lowercase().contains(nameQ) &&
                    it.canton.orEmpty().lowercase().contains(cantonQ)
        }.sortedWith(compareBy { record ->
            when (_sortOption.value) {
                "Entitet" -> record.entity.orEmpty().lowercase()
                "Muških" -> -record.maleTotal
                "Ženskih" -> -record.femaleTotal
                else -> -record.total
            }
        })

        _filtered.value = filtered
    }

    fun toggleFavorite(newborn: NewbornInfo) {
        viewModelScope.launch {
            val updated = newborn.copy(isFavorite = !newborn.isFavorite)
            repository.updateFavorite(updated)
            _newborns.value = repository.getAllLocal().map { it.toInfo() }
        }
    }

    suspend fun getFavoriteNewborns(): List<NewbornInfo> {
        return repository.getFavorites()
    }

    private val _chartData = MutableStateFlow<List<Float>>(emptyList())
    val chartData: StateFlow<List<Float>> = _chartData

    fun loadChartData() {
        viewModelScope.launch {
            val all = repository.getAllLocal()
            if (all.isEmpty()) return@launch

            val countFBiH = repository.countByEntity("FEDERACIJA BOSNE I HERCEGOVINE").toFloat()
            val countRS = repository.countByEntity("REPUBLIKA SRPSKA").toFloat()
            _chartData.value = listOf(countFBiH, countRS)
        }
    }



}
