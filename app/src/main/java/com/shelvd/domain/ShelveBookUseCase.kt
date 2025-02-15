package com.shelvd.domain

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShelveBookUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
){

    suspend operator fun invoke(newBook: ShelvedBook)=
        withContext(defaultDispatcher) {
            bookRepository.addBookToShelf(newBook)
        }
}
