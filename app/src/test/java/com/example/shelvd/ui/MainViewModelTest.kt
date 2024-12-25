package com.example.shelvd.ui


import com.shelvd.data.repo.DefaultBookRepository
import com.shelvd.ui.BookIntent
import com.shelvd.ui.BookListViewState
import com.shelvd.ui.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
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
class MainViewModelTest {

    private val repo = DefaultBookRepository()
    @OptIn(DelicateCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun `load books`() = runTest(UnconfinedTestDispatcher()) {
          val vm = MainViewModel(repo)
            vm.handleIntent(BookIntent.LoadBooks)
            assertEquals(vm.state.value, BookListViewState.BooksLoaded(repo.getBooks()))
            val loadedState = vm.state.value as BookListViewState.BooksLoaded
            assertTrue(loadedState.books.isNotEmpty())
    }
}
