package com.shelvd.domain

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsbnLookUpUseCase @Inject constructor(
    private val bookRepository: BookRepository,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend operator fun invoke(isbn: String): Flow<Pair<ShelvedBook?, Boolean>>{
       return withContext(dispatcher){
           bookRepository.lookUpBookByISBN(isbn).map { newBook ->
                checkForDuplicate(newBook)
            }
        }
    }

    private fun checkForDuplicate(newBook: ShelvedBook?): Pair<ShelvedBook?, Boolean> =
        newBook?.let { bookRepository.checkForDuplicate(it)?.let { dup -> Pair(dup, true) } }
            ?: Pair(newBook, false)

}
