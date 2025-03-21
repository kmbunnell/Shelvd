package com.example.shelvd.domain

import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.ReShelveBookUseCase
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ReShelfBookUseCaseTest {
    val shelvedBook = ShelvedBook(
        listOf("Sarah J Maas"),
        "A Court of Thorns and Roses", isbn = "isbn", Shelf.WANT
    )

    val bookRepository = mock<BookRepository>() {
        on { loadShelvedBooks() }.thenReturn(
            listOf(
                shelvedBook
            )
        )
    }

    @Test
    fun `book reshelved`() {

        val reshelvedBook = ShelvedBook(
            listOf("Sarah J Maas"),
            "A Court of Thorns and Roses", isbn = "isbn", Shelf.OWNED
        )

        val usecase = ReShelveBookUseCase(bookRepository)
        usecase.invoke(shelvedBook, Shelf.OWNED)
        verify(bookRepository).updateBookShelf(reshelvedBook)
    }
}
