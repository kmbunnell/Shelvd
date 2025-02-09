package com.shelvd.data.api

import com.shelvd.data.model.ApiResult
import com.shelvd.data.Util
import com.shelvd.data.model.BookResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiService {
    fun getBookByIsbn(isbn:String): Flow<ApiResult<BookResult>>
}

class OpenLibraryApiImpl @Inject constructor(private val httpClient: HttpClient): ApiService {
    override fun getBookByIsbn(isbn: String): Flow<ApiResult<BookResult>> = flow {
        try {
            emit(
                ApiResult.Success(
                    httpClient.request(String.format("${Util.BASE_URL}/search.json?isbn=$isbn"))
                        .body<BookResult>()
                )
            )
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message ?: "getBook call failed"))
        }
    }

}