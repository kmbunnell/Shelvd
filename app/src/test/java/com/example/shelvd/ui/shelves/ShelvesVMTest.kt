package com.example.shelvd.ui.shelves

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import com.shelvd.domain.LoadBooksForShelfUseCase
import com.shelvd.ui.screens.shelves.ShelvesVM
import com.shelvd.ui.screens.shelves.ShelvesViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ShelvesVMTest {

    val mockBookService:LoadBooksForShelfUseCase = mock()
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val testOwnedShelvedBooks= listOf(
        ShelvedBook(listOf("Kristina Bunnell"), "Most Amazing Book Ever", "isbn", Shelf.OWNED),
        ShelvedBook(listOf("K Bear"), "Hot Vampires","isbn", Shelf.OWNED)
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `load books`() = runTest(testDispatcher) {
        whenever(mockBookService.invoke(Shelf.OWNED)).thenReturn(testOwnedShelvedBooks)
        val vm = ShelvesVM(mockBookService)
        assertEquals(vm.state.value, ShelvesViewState.ShelvedBooks(shelvedBooks = testOwnedShelvedBooks))

    }
}