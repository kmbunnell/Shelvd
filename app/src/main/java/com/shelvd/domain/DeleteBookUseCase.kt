package com.shelvd.domain

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import javax.inject.Inject

class DeleteBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
) {
    operator fun invoke(book: ShelvedBook) =
        bookRepository.removeBookFromShelf(book)
}