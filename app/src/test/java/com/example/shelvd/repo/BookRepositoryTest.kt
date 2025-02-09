package com.example.shelvd.repo

import com.shelvd.data.api.ApiService
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.Doc
import com.shelvd.data.repo.DefaultBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class BookRepositoryTest {
    val testDispatcher = UnconfinedTestDispatcher()
    val mockApi = mock<ApiService> {
        on { getBookByIsbn("isbn") }.thenReturn(flow {
            emit(
                ApiResult.Success(
                    BookResult(
                        listOf(
                            Doc(
                                listOf("Sarah J Maas"),
                                "A Court of Thorns and Roses"
                            )
                        )
                    )
                )
            )
        })
    }
    val bookRepo = DefaultBookRepository(mockApi)
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
    fun `lookUpBookByISBN returns ShelvedBook`() =  runTest(testDispatcher) {
        val shelvedBook = bookRepo.lookUpBookByISBN("isbn").first()
        assertEquals(shelvedBook?.title,"A Court of Thorns and Roses")
    }
    
}