package com.shelvd.domain

import com.shelvd.data.model.Shelf
import com.shelvd.data.repo.BookRepository
import javax.inject.Inject

class LoadBooksByShelfUseCase @Inject constructor(private val bookRepository: BookRepository) {
    operator fun invoke(shelf: Shelf) = bookRepository.getShelvedBooksByShelf(shelf)
}
