package com.shelvd.domain

import com.shelvd.data.api.ApiSevice
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.bookData.BookData
import com.shelvd.di.DefaultDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsbnLookUpUseCase @Inject constructor(
    private val apiService: ApiSevice,
    @DefaultDispatcher
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend operator fun invoke(isbn: String): Flow<ApiResult<BookData>> =
        withContext(dispatcher) {
           apiService.getBookByIsbn(isbn)
        }
}