package com.shelvd.data.api

import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.Util
import com.shelvd.data.model.bookData.BookData
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiSevice {
    fun getBookByIsbn(isbn:String): Flow<ApiResult<BookData>>
}

class OpenLibraryApiImpl @Inject constructor(private val httpClient: HttpClient): ApiSevice
{
    override fun getBookByIsbn(isbn: String): Flow<ApiResult<BookData>> = flow{
       try{

           emit(ApiResult.Success( httpClient.request(String.format("${Util.BASE_URL}/isbn/ $isbn.json")).body<BookData>()))
        }
        catch (e:Exception){
            emit (ApiResult.Error(e.message?: "getBook call failed"))
        }
    }
}