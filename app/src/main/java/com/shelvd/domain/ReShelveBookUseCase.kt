package com.shelvd.domain

import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import javax.inject.Inject

class ReShelveBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(book: ShelvedBook, newShelf: Shelf) =
        bookRepository.updateBookShelf(book.copy(shelf = newShelf))
}