package com.example.shelvd.repo

import com.shelvd.data.api.ApiService
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.Doc
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.DefaultBookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

class BookRepositoryTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
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
    val bookRepo = DefaultBookRepository(mockApi, testDispatcher)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `lookUpBookByISBN returns ShelvedBook`() = runTest(testDispatcher) {
        val shelvedBook = bookRepo.lookUpBookByISBN("isbn").first()
        assertEquals(shelvedBook?.title, "A Court of Thorns and Roses")
    }

    @Test
    fun `load books from Owned Shelf`() = runTest(testDispatcher) {
        val expected = listOf(
            ShelvedBook(
                listOf("Sarah J Maas"),
                "A Court of Silver Flames",
                isbn = "",
                Shelf.OWNED
            ),
            ShelvedBook(
                listOf("Jay Kristoff"),
                "Empire of the Vampire",
                isbn = "",
                Shelf.OWNED
            ),
            ShelvedBook(
                listOf("Brigid Kemmerer"),
                "Defy the Night",
                isbn = "",
                Shelf.OWNED
            ),
            ShelvedBook(
                listOf("Brandon Sanderson"),
                "Mistborn: The final Empire",
                isbn = "",
                Shelf.OWNED
            )
        )
        val books = bookRepo.getShelvedBooksByShelf(Shelf.OWNED)
        assertEquals(books, expected)

    }

    @Test
    fun `load books from want Shelf`() = runTest(testDispatcher) {
        val expected = listOf(
            ShelvedBook(
                authors = listOf("Jennifer Armentrout"),
                title = "A Soul of Blood and Ash",
                isbn = "",
                shelf = Shelf.WANT
            )
        )
        val books = bookRepo.getShelvedBooksByShelf(Shelf.WANT)
        assertEquals(books, expected)

    }

}