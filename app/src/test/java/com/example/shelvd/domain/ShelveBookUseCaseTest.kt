package com.example.shelvd.domain

import com.shelvd.data.model.Edition
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.ShelveBookUseCase
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ShelveBookUseCaseTest {

    val bookRepository =  mock<BookRepository>() {
        on { loadShelvedBooks() }.thenReturn(
            listOf(
                ShelvedBook(
                    listOf("Sarah J Maas"),
                    "A Court of Thorns and Roses", isbn = "isbn", Shelf.WANT
                )
            )
        )
    }

    @Test
    fun `book add to shelf success`() {
        val newBook = ShelvedBook(
            listOf("K bear"),
            "Amazing Book  ",
            isbn = "7897",
            Shelf.WANT
        )
        val usecase = ShelveBookUseCase(bookRepository)
        usecase.invoke(
            newBook = newBook,
            newShelf = Shelf.WANT
        )
        verify(bookRepository).addBookToShelf(newBook)

    }
}