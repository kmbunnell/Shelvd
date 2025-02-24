package com.example.shelvd.domain

import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ShelveBookUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ShelveBookUseCaseTest {

    val bookRepository = mock<BookRepository>() {
        on {getShelvedBooksByShelf(Shelf.WANT)}.thenReturn(
           listOf(ShelvedBook(
                    listOf("Sarah J Maas"),
                    "A Court of Thorns and Roses", isbn = "isbn", Shelf.WANT
                )))
    }

    @Test
    fun `book lookup success`(){
        val newBook =ShelvedBook(
            listOf("K bear"),
            "Amazing Book  ",
            isbn = "7897",
            Shelf.WANT
        )
        val usecase = ShelveBookUseCase(bookRepository)
        usecase.invoke(newBook)
        verify(bookRepository).addBookToShelf(newBook)
    }
}