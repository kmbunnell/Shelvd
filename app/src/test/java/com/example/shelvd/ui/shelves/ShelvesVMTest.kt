package com.example.shelvd.ui.shelves

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.DeleteBookUseCase
import com.shelvd.domain.GetBooksByShelfUseCase
import com.shelvd.domain.ReShelveBookUseCase
import com.shelvd.domain.ShelveBookUseCase
import com.shelvd.ui.screens.shelves.ShelvesIntent
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
import org.mockito.kotlin.verify

import org.mockito.kotlin.whenever

class ShelvesVMTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val testOwnedShelvedBooks = listOf(
        ShelvedBook(listOf("Kristina Bunnell"), "Most Amazing Book Ever", "isbn", Shelf.OWNED),
        ShelvedBook(listOf("K Bear"), "Hot Vampires", "isbn", Shelf.OWNED)
    )

    private val booksByShelfUseCaseMock = mock<GetBooksByShelfUseCase>()
    private val reShelveBookUseCaseMock = mock<ReShelveBookUseCase>()
    private val deleteBookUseCaseMock = mock<DeleteBookUseCase>()

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
    fun `load owned books`() = runTest(testDispatcher) {
        whenever(booksByShelfUseCaseMock.invoke(Shelf.OWNED)).thenReturn(testOwnedShelvedBooks)
        val vm = ShelvesVM(
            booksByShelfUseCase = booksByShelfUseCaseMock,
            deleteBookUseCase = deleteBookUseCaseMock,
            reshelveBookUseCase = reShelveBookUseCaseMock
        )
        vm.handleIntent(ShelvesIntent.LoadBooks(Shelf.OWNED))
        assertEquals(
            vm.state.value,
            ShelvesViewState.ShelvedBooks(
                currentShelf = Shelf.OWNED,
                shelvedBooks = testOwnedShelvedBooks
            )
        )
    }

    @Test
    fun `Reshelve intent calls reshelve usecase`() = runTest(testDispatcher) {
        val vm = ShelvesVM(
            booksByShelfUseCase = booksByShelfUseCaseMock,
            deleteBookUseCase = deleteBookUseCaseMock,
            reshelveBookUseCase = reShelveBookUseCaseMock
        )
        val currentBook =  ShelvedBook(listOf("K Bear"), "Hot Vampires", "isbn", Shelf.OWNED)
        val newShelf = Shelf.WANT
        vm.handleIntent(ShelvesIntent.ReshelveBook(newShelf = newShelf, book = currentBook ))
        verify(reShelveBookUseCaseMock).invoke(newShelf = newShelf, book = currentBook)

    }

    @Test
    fun `Delete intent calls delete use case`() = runTest(testDispatcher) {

        val vm = ShelvesVM(
            booksByShelfUseCase = booksByShelfUseCaseMock,
            deleteBookUseCase = deleteBookUseCaseMock,
            reshelveBookUseCase = reShelveBookUseCaseMock
        )
        val currentBook =  ShelvedBook(listOf("K Bear"), "Hot Vampires", "isbn", Shelf.OWNED)
        val newShelf = Shelf.WANT
        vm.handleIntent(ShelvesIntent.ReshelveBook(newShelf = newShelf, book = currentBook ))
        verify(reShelveBookUseCaseMock).invoke(newShelf = newShelf, book = currentBook)

    }
}