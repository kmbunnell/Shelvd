package com.example.shelvd.ui

import com.shelvd.data.model.Book
import com.shelvd.data.model.Shelf
import com.shelvd.domain.LoadBooksForShelf
import com.shelvd.ui.screens.shelves.ShelvesIntent
import com.shelvd.ui.screens.shelves.ShelvesVM
import com.shelvd.ui.screens.shelves.ShelvesViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class ShelvesVMTest {
    val mockBookService : LoadBooksForShelf = mock()
   val testOwnedBooks= listOf(Book("Kristina Bunnell", "Most Amazing Book Ever"), Book("K Bear", "Hot Vampires"))
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `load books`() = runTest(UnconfinedTestDispatcher()) {
        whenever(mockBookService.invoke(Shelf.OWNED)).thenReturn(testOwnedBooks)
        val vm = ShelvesVM(mockBookService)
        vm.handleIntent(ShelvesIntent.LoadBooks(Shelf.OWNED))
        assertEquals(vm.state.value, ShelvesViewState.ShelvedBooks(books = testOwnedBooks))
      //not passing
    }
}