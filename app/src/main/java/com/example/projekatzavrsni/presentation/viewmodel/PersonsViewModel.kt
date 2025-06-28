package com.example.projekatzavrsni.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projekatzavrsni.data.api.persons.PersonInfo
import com.example.projekatzavrsni.data.local.RoomInstance
import com.example.projekatzavrsni.data.repository.PersonsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PersonsViewModel : ViewModel() {
    fun initRepository(context: Context) {
        val db = RoomInstance.getDatabase(context)
        val dao = db.personDao()
        repository = PersonsRepository(dao)
    }

    lateinit var repository: PersonsRepository

    private val _persons = MutableStateFlow<List<PersonInfo>>(emptyList())
    val persons: StateFlow<List<PersonInfo>> = _persons

    private val _filtered = MutableStateFlow<List<PersonInfo>>(emptyList())
    val filtered: StateFlow<List<PersonInfo>> = _filtered

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _cantonQuery = MutableStateFlow("")
    val cantonQuery: StateFlow<String> = _cantonQuery

    private val _sortOption = MutableStateFlow("Ukupno")
    val sortOption: StateFlow<String> = _sortOption



    fun fetchPersons() {
        viewModelScope.launch {
            val result = repository.getPersons()
            _persons.value = result
            applyFilterAndSort()
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

        val filtered = _persons.value.filter {
            it.municipality.orEmpty().lowercase().contains(nameQ) &&
                    it.canton.orEmpty().lowercase().contains(cantonQ)
        }.sortedWith(compareBy { record ->
            when (_sortOption.value) {
                "Entitet" -> record.entity.orEmpty().lowercase()
                "Institucija" -> record.institution.orEmpty().lowercase()
                "Sa prebivalištem" -> -record.withResidenceTotal
                else -> -record.total
            }
        })

        _filtered.value = filtered
    }

    fun toggleFavorite(person: PersonInfo) {
        viewModelScope.launch {
            val updated = person.copy(isFavorite = !person.isFavorite)
            println(">>> FAVORITE TOGGLE PERSONS: ${updated.institution} / ${updated.dateUpdate} -> ${updated.isFavorite}")
            repository.updateFavorite(updated)
            fetchPersons()
        }
    }
    suspend fun getFavoritePersons(): List<PersonInfo> {
        return repository.getFavorites()
    }

    private val _cantonChartData = MutableStateFlow<Map<String, Int>>(emptyMap())
    val cantonChartData: StateFlow<Map<String, Int>> = _cantonChartData

    fun loadCantonChartData() {
        viewModelScope.launch {
            val data = repository.getAllLocalPersons()
                .filter { it.entity == "FEDERACIJA BOSNE I HERCEGOVINE" }
                .groupingBy { it.canton ?: "Nepoznato" }
                .eachCount()
                .toList()
                .sortedByDescending { it.second }
                .toMap()

            _cantonChartData.value = data
        }
    }


}
