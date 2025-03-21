package com.shelvd.domain

import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import javax.inject.Inject

class GetBooksByShelfUseCase @Inject constructor(private val bookRepository: BookRepository) {
    operator fun invoke(shelf: Shelf): List<ShelvedBook> {
        return bookRepository.loadShelvedBooks().filter { it.shelf == shelf }
    }
}