package com.shelvd.data.api

import com.shelvd.data.model.ApiResult
import com.shelvd.data.Util
import com.shelvd.data.model.Book
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiSevice {
    fun getBookByIsbn(isbn:String): Flow<ApiResult<Book>>
}

class OpenLibraryApiImpl @Inject constructor(private val httpClient: HttpClient): ApiSevice {
    override fun getBookByIsbn(isbn: String): Flow<ApiResult<Book>> = flow {
        try {
            emit(
                ApiResult.Success(
                    httpClient.request(String.format("${Util.BASE_URL}/search.json?isbn=$isbn"))
                        .body<Book>()
                )
            )
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message ?: "getBook call failed"))
        }
    }

}