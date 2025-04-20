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
import org.mockito.kotlin.whenever

class IsbnLookupUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val shelvedBook = ShelvedBook(
        listOf("Sarah J Maas"),
        "A Court of Thorns and Roses", isbn = "isbn", Shelf.WANT
    )
    val bookRepository = mock<BookRepository>() {
        onBlocking { lookUpBookByISBN("isbn") }.thenReturn(flow {
            emit(shelvedBook)
        })
    }

    @Test
    fun `book lookup success`() = runTest(testDispatcher) {
        val usecase = IsbnLookUpUseCase(bookRepository, testDispatcher)
        val item = usecase.invoke("isbn").first()
        assertEquals(item.first?.title, "A Court of Thorns and Roses")
    }

    @Test
    fun `book lookup duplicate`() = runTest(testDispatcher) {
        whenever(bookRepository.checkForDuplicate(shelvedBook)).thenReturn(shelvedBook)
        val usecase = IsbnLookUpUseCase(bookRepository, testDispatcher)
        val item = usecase.invoke("isbn").first()
        assertEquals(item.first?.title, "A Court of Thorns and Roses")
        assertEquals(item.second, true)
    }

}