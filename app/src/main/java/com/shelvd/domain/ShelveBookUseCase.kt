package com.shelvd.domain

import com.shelvd.data.model.Edition
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import javax.inject.Inject

class ShelveBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(newBook: ShelvedBook, newShelf: Shelf, editionFlags: List<Edition>) {

        val book = newBook.updateEditionFlags(editionFlags).copy(shelf = newShelf)
        bookRepository.addBookToShelf(book)
    }
}