package com.shelvd.domain

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class IsbnLookUpUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(isbn: String): Flow<ShelvedBook?> =
        bookRepository.lookUpBookByISBN(isbn).flowOn(dispatcher)
}