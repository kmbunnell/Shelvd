package com.example.shelvd.domain


import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.IsbnLookUpUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class IsbnLookupUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val bookRepository = mock<BookRepository>() {
        on { lookUpBookByISBN("isbn") }.thenReturn(flow {
            emit(
                ShelvedBook(
                    listOf("Sarah J Maas"),
                    "A Court of Thorns and Roses", isbn = "isbn", Shelf.WANT
                )
            )
        })
    }

    @Test
    fun `book lookup success`() = runTest(testDispatcher) {
        val usecase = IsbnLookUpUseCase(bookRepository, testDispatcher)
        val item = usecase.invoke("isbn").first()
        assertEquals(item.first?.title, "A Court of Thorns and Roses")
    }

}