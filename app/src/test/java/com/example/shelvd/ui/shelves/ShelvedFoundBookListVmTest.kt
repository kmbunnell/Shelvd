package com.example.shelvd.ui.shelves


import com.shelvd.data.repo.DefaultBookRepository
import com.shelvd.ui.screens.bookList.BookIntent
import com.shelvd.ui.screens.bookList.BookListViewState
import com.shelvd.ui.screens.bookList.BookListVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test



@OptIn(ExperimentalCoroutinesApi::class)
class ShelvedFoundBookListVmTest {

    private val repo = DefaultBookRepository()
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()

    }

    @Test
    fun `load books`() = runTest(testDispatcher) {
          val vm = BookListVM(repo)
            vm.handleIntent(BookIntent.LoadBooks)
            assertEquals(vm.state.value, BookListViewState.BooksLoaded(repo.getBooks()))
            val loadedState = vm.state.value as BookListViewState.BooksLoaded
            assertTrue(loadedState.shelvedBooks.isNotEmpty())
    }

    @Test
    fun `add book`() = runTest(testDispatcher) {
        val vm = BookListVM(repo)
        vm.handleIntent(BookIntent.AddBook)
        val loadedState = vm.state.value as BookListViewState.BooksLoaded
        assertTrue(loadedState.shelvedBooks.isNotEmpty())
        assertTrue(loadedState.shelvedBooks.size==8)
    }

    @Test
    fun `remove book`() = runTest(testDispatcher) {
        val vm = BookListVM(repo)
        vm.handleIntent(BookIntent.RemoveBook(2))
        val loadedState = vm.state.value as BookListViewState.BooksLoaded
        assertTrue(loadedState.shelvedBooks.isNotEmpty())
        assertTrue(loadedState.shelvedBooks.size==6)
    }

    @Test
    fun `remove book no change when idx -1`() = runTest(testDispatcher) {
        val vm = BookListVM(repo)
        vm.handleIntent(BookIntent.RemoveBook(-1))
        val loadedState = vm.state.value as BookListViewState.BooksLoaded
        assertTrue(loadedState.shelvedBooks.isNotEmpty())
        assertTrue(loadedState.shelvedBooks.size==7)
    }
}
