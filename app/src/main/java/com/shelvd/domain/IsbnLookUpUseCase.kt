package com.shelvd.domain

import com.shelvd.data.api.ApiService
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.BookResult
import com.shelvd.di.DefaultDispatcher
import com.shelvd.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class IsbnLookUpUseCase @Inject constructor(
    private val apiService: ApiService,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(isbn: String): Flow<ApiResult<BookResult>> =
        withContext(dispatcher) {
           apiService.getBookByIsbn(isbn)
        }
}