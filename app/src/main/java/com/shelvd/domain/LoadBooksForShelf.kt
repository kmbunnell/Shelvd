package com.shelvd.domain

import com.shelvd.data.model.Book
import com.shelvd.data.model.Shelf
import com.shelvd.data.repo.BookRepository
import com.shelvd.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadBooksForShelf @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
){

    suspend operator fun invoke(shelf: Shelf): List<Book> =
        withContext(defaultDispatcher) {
            bookRepository.getBooks().filter { it.shelf == shelf }
        }
}