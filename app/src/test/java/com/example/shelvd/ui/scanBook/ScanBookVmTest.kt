package com.example.shelvd.ui.scanBook

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ScanBookUseCase
import com.shelvd.domain.ShelveBookUseCase
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
import org.mockito.kotlin.whenever


class ScanFoundBookVmTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
    val shelvedBook = Pair(ShelvedBook(listOf("K Bear"), "Best book", isbn = "12345"), false)
    val dupBook = Pair(ShelvedBook(listOf("K Bear"), "Best book Two", isbn = "6789"), true)
    private val mockScanBookUseCase = mock<ScanBookUseCase>() {
        on { invoke() }.thenReturn(flow {
            emit(shelvedBook)
        })
    }

    private val mockIsbnLookUpUseCase = mock<IsbnLookUpUseCase>() {
        onBlocking { invoke("12345") }.thenReturn(flow {
            emit(shelvedBook)
        })
    }
    private val shelveBookUseCase = mock<ShelveBookUseCase>()

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
            shelveBookUseCase = shelveBookUseCase
        )
        vm.handleIntent(ScanBookIntent.StartScan)
        assertEquals(vm.state.value, ScanBookViewState.BookScanSuccess(shelvedBook.first, false))
    }

    @Test
    fun `when isbn look up successful, UI state is BookScanSuccess`() = runTest(testDispatcher) {
        val vm = ScanBookVm(
            scanBookUseCase = mockScanBookUseCase,
            isbnLookUpUseCase = mockIsbnLookUpUseCase,
            shelveBookUseCase = shelveBookUseCase
        )
        vm.handleIntent(ScanBookIntent.LookUp("12345"))
        assertEquals(vm.state.value, ScanBookViewState.BookScanSuccess(shelvedBook.first, false))
    }

    @Test
    fun `when duplicate is detected, isDup is true`() = runTest(testDispatcher) {
        whenever(mockIsbnLookUpUseCase.invoke("6789")).thenReturn(flow {
            emit(dupBook)
        })

        val vm = ScanBookVm(
            scanBookUseCase = mockScanBookUseCase,
            isbnLookUpUseCase = mockIsbnLookUpUseCase,
            shelveBookUseCase = shelveBookUseCase
        )
        vm.handleIntent(ScanBookIntent.LookUp("6789"))
        assertEquals(vm.state.value, ScanBookViewState.BookScanSuccess(dupBook.first, true))
    }

}