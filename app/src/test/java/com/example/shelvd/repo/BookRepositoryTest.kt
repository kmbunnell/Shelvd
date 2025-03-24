package com.example.shelvd.repo

import com.shelvd.data.Util
import com.shelvd.data.api.ApiService
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.Doc
import com.shelvd.data.model.Edition
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.DefaultBookRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
    val bookRepo = DefaultBookRepository(mockApi)

    /*@OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {

        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }*/

    @Test
    fun `lookUpBookByISBN returns ShelvedBook`() = runTest(testDispatcher) {
        val shelvedBook = bookRepo.lookUpBookByISBN("isbn").first()
        assertEquals(shelvedBook?.title, "A Court of Thorns and Roses")
    }

    @Test
    fun `find duplicate`() {
        val newBook = ShelvedBook(
            listOf("Jay Kristoff"),
            "Empire of the Vampire",
            isbn = "2222",
            Shelf.OWNED,
            Util.calculateEditionFlags(listOf(Edition.SPECIAL, Edition.SIGNED)),
            ""
        )
        bookRepo.loadShelvedBooks()
        val dup = bookRepo.checkForDuplicate(newBook)

        assertEquals(newBook, dup)
    }

    @Test
    fun `no duplicate`() {
        val newBook = ShelvedBook(
            listOf("K bear"),
            "Amazing Book  ",
            isbn = "7897",
            Shelf.WANT
        )

        val dup = bookRepo.checkForDuplicate(newBook)

        assertEquals(dup, null)
    }

    @Test
    fun `add new book()`()
    {
        val newBook =ShelvedBook(
        listOf("K bear"),
        "Amazing Book  ",
        isbn = "7897",
        Shelf.WANT
        )
       val beforeCount = bookRepo.loadShelvedBooks().size
        bookRepo.addBookToShelf(newBook)
        val afterCount = bookRepo.loadShelvedBooks().size
        assertEquals(afterCount, beforeCount+1)

    }

    @Test
    fun `update shelved book()`()
    {
        val flagInt= Util.calculateEditionFlags(listOf(Edition.PAPERBACK))
        val updateBook =  ShelvedBook(
        listOf("Brynne Weaver"),
        "Scythe and Sparrow",
        isbn = "6666",
        Shelf.OWNED,
            flagInt,
            "some note"

        )

        //look up book in booklist
        val beforeCount = bookRepo.loadShelvedBooks().size
        bookRepo.updateBookShelf(updateBook)
        val afterCount = bookRepo.loadShelvedBooks().size

        assertEquals(afterCount, beforeCount) //book list count remains the same

        //relook up book after update
        val bookUpdated = bookRepo.loadShelvedBooks().first{it.isbn == "6666"}
        assertEquals(bookUpdated.shelf, Shelf.OWNED)
        assertEquals(bookUpdated.editionFlags, flagInt)
        assertEquals(bookUpdated.notes, "some note")

    }
}