package com.shelvd.domain

import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import javax.inject.Inject

class MoveBookUseCase @Inject constructor(private val bookRepository: BookRepository) {
    operator fun invoke(newBook: ShelvedBook, shelf: Shelf) = bookRepository.moveBookToNewShelf(newBook, shelf)
}
