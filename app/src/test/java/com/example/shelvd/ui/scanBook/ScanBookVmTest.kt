package com.example.shelvd.ui.scanBook

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ScanBookUseCase
import com.shelvd.ui.screens.scanBook.ScanBookIntent
import com.shelvd.ui.screens.scanBook.ScanBookViewState
import com.shelvd.ui.screens.scanBook.ScanBookVm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow

import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.junit.Assert.assertEquals


class ScanFoundBookVmTest {


    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    val shelvedBook = ShelvedBook(listOf("K Bear"), "Best book", isbn = "12345")
    private val mockScanBookUseCase = mock<ScanBookUseCase>() {
        on { invoke() }.thenReturn((flow {
            emit(shelvedBook)
        }))
    }

    private val mockIsbnLookUpUseCase = mock<IsbnLookUpUseCase>() {
        on { invoke("12345") }.thenReturn(flow {
            emit(shelvedBook)
        })
    }

    private val mockBookRepo = mock<BookRepository>()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher

    }


    @Test
    fun `when scan successful, UI state is BookScanSuccess`() = runTest(testDispatcher) {
        val vm = ScanBookVm(
            scanBookUseCase = mockScanBookUseCase,
            isbnLookUpUseCase = mockIsbnLookUpUseCase,
            bookRepository = mockBookRepo
        )
        vm.handleIntent(ScanBookIntent.StartScan)
        assertEquals(vm.state.value, ScanBookViewState.BookScanSuccess(shelvedBook))
    }

    @Test
    fun `when isbn look up successful, UI state is BookScanSuccess`() = runTest(testDispatcher) {
        val vm = ScanBookVm(
            scanBookUseCase = mockScanBookUseCase,
            isbnLookUpUseCase = mockIsbnLookUpUseCase,
            bookRepository = mockBookRepo
        )
        vm.handleIntent(ScanBookIntent.LookUp("12345"))
        assertEquals(vm.state.value, ScanBookViewState.BookScanSuccess(shelvedBook))
    }

}