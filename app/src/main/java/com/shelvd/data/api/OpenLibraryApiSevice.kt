package com.shelvd.data.api

import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.Util
import com.shelvd.data.model.bookData.BookData
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface ApiSevice {
    fun getBook(isbn:String): Flow<ApiResult<BookData>>
}

class OpenLibraryApiImpl @Inject constructor(private val httpClient: HttpClient): ApiSevice
{
    override fun getBook(isbn: String): Flow<ApiResult<BookData>> = flow{
        emit(ApiResult.Loading())

       try{
           val response: HttpResponse =  httpClient.request(String.format("${1}/${2}.json", Util.BASE_URL, isbn))

            //create usecase and wire up to button, see what this looks like.
            /*emit(ApiResult.Success(
              httpClient.request(String.format("${1}/${2}.json", Util.BASE_URL, isbn))
            ))*/
        }
        catch (e:Exception){
            emit (ApiResult.Error(e.message?: "getBook call failed"))
        }
    }
}