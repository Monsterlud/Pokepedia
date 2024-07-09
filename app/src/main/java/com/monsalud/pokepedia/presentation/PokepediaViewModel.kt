package com.monsalud.pokepedia.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.monsalud.pokepedia.data.datasource.utils.EntityMappers
import com.monsalud.pokepedia.domain.Pokemon
import com.monsalud.pokepedia.domain.PokepediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class PokepediaViewModel(
    private val pokepediaRepository: PokepediaRepository,
    private val entityMappers: EntityMappers,
) : ViewModel() {

    /**
     * State Flow for UI to use
     */
    private val _pokemon = MutableStateFlow<List<Pokemon>>(emptyList<Pokemon>())
    val pokemon = _pokemon.asStateFlow()

    private val _isInitialLoading = MutableStateFlow(true)
    val isLoading = _isInitialLoading.asStateFlow()

    private val _filterText = MutableStateFlow("")
    val filterText = _filterText.asStateFlow()

    private val _filteredPokemon = MutableStateFlow<List<Pokemon>>(emptyList())
    val filteredPokemon = _filteredPokemon.asStateFlow()

    private var currentPage = 0
    private val pageSize = 10

    init {
        loadInitialPage()
    }

    fun clearFilter() {
        updateFilter("")
    }

    fun updateFilter(newFilter: String) {
        viewModelScope.launch {
            _filterText.value = newFilter
            updateFilteredPokemon()
            Timber.d("****Filter Updated in updateFilter(): $newFilter")
        }
    }

    fun loadInitialPage() {
        viewModelScope.launch {
            _isInitialLoading.value = true
            try {
                loadPage()
                updateFilteredPokemon()
            } finally {
                _isInitialLoading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_isInitialLoading.value) return

        viewModelScope.launch {
            try {
                loadPage()
                updateFilteredPokemon()
            } catch (e: Exception) {
                Timber.e(e, "Error loading next page")
            }
        }
    }

    private fun updateFilteredPokemon() {
        val currentFilter = _filterText.value
        val currentPokemonList = _pokemon.value
        _filteredPokemon.value = if (currentFilter.isBlank()) {
            currentPokemonList
        } else {
            currentPokemonList.filter { it.name.contains(currentFilter, ignoreCase = true) }
        }
    }

    suspend fun loadPage() {
        withContext(Dispatchers.IO) {
            pokepediaRepository.getAndSavePokepediaList(pageSize, currentPage * pageSize)
            val newPokemonList = pokepediaRepository.retrievePokemonListFromLocalStorage(
                pageSize,
                currentPage * pageSize
            ).first().map { entity -> entityMappers.mapFromEntityToPokemon(entity) }

            if (newPokemonList.isNotEmpty()) {
                val currentList = _pokemon.value
                val updatedList = currentList + newPokemonList.filter { newPokemon ->
                    currentList.none { it.id == newPokemon.id }
                }
                _pokemon.value = updatedList
                currentPage++
            }
        }
    }
}