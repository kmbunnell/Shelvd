package com.example.shelvd.domain

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.LoadBooksForShelfUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.kotlin.mock


class LoadBooksForShelfUseCaseTest {
    val testShelvedBooks= listOf(
        ShelvedBook("author", "cool title", Shelf.OWNED),
        ShelvedBook("super author", "cooler title", Shelf.OWNED),
        ShelvedBook("author misc", "cool title"))
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val bookrepo = mock<BookRepository>{
        on{getBooks()}.thenReturn(testShelvedBooks)
    }


    @Test
    fun `load books from Owned Shelf`() = runTest(testDispatcher) {
        val expected = listOf(
        ShelvedBook("author", "cool title", Shelf.OWNED),
        ShelvedBook("super author", "cooler title", Shelf.OWNED))
       val usecase = LoadBooksForShelfUseCase(bookrepo, testDispatcher)
        val books = usecase.invoke(Shelf.OWNED)
        assertEquals(books, expected)

    }
    @Test
    fun `load books from Misc Shelf`() = runTest(testDispatcher) {
        val expected = listOf( ShelvedBook("author misc", "cool title") )
        val usecase = LoadBooksForShelfUseCase(bookrepo, testDispatcher)
        val books = usecase.invoke(Shelf.MISC)
        assertEquals(books, expected)

    }
}