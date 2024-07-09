package com.monsalud.pokepedia.presentation

import com.monsalud.pokepedia.MainCoroutineRule
import com.monsalud.pokepedia.data.datasource.remote.NetworkUtils
import com.monsalud.pokepedia.data.datasource.utils.EntityMappers
import com.monsalud.pokepedia.domain.PokepediaRepository
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PokepediaViewModelTest {

    private lateinit var viewModel: PokepediaViewModel
    private lateinit var repository: PokepediaRepository
    private lateinit var networkUtils: NetworkUtils
    private lateinit var entityMappers: EntityMappers

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = mockk()
        networkUtils = mockk()
        entityMappers = mockk()
        viewModel = PokepediaViewModel(repository, entityMappers)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `updateFilter updates _filterText value`() {
        val newFilterText = "Pikachu"
        viewModel.updateFilter(newFilterText)
        assertEquals(newFilterText, viewModel.filterText.value)
    }

    @Test
    fun `clearFilter clears _filterText value`() {
        viewModel.updateFilter("Pikachu")
        viewModel.clearFilter()
        assertEquals("", viewModel.filterText.value)
    }
}