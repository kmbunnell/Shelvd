package com.shelvd.domain

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import com.shelvd.data.repo.BookRepository
import com.shelvd.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoadBooksForShelfUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
){

    suspend operator fun invoke(shelf: Shelf): List<ShelvedBook> =
        withContext(defaultDispatcher) {
            bookRepository.getShelvedBooks().filter { it.shelf == shelf }
        }
}