package com.example.shelvd.domain

import com.shelvd.data.api.ApiService
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.Doc
import com.shelvd.domain.IsbnLookUpUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock

class IsbnLookupUseCaseTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()

    @Test
    fun `book lookup success`() = runTest(testDispatcher) {
        val mockApi = mock<ApiService> {
            on { getBookByIsbn("isbn") }.thenReturn(flow {
                emit(
                    ApiResult.Success(
                        BookResult(
                            listOf(
                                Doc(
                                    listOf("Sarah J Maas"),
                                    "A Court of Thorns and Roses"
                                )
                            )
                        )
                    )
                )
            })
        }
        val usecase = IsbnLookUpUseCase(mockApi, testDispatcher)
        val item = usecase.invoke("isbn").first()
        assertEquals(item.data?.docs?.first()?.title, "A Court of Thorns and Roses")
    }

}