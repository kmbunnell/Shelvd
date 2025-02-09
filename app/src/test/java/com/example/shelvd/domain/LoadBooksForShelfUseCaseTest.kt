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
        ShelvedBook(listOf("author"), "cool title", "isbn", Shelf.OWNED),
        ShelvedBook(listOf("super author"), "cooler title", "isbn", Shelf.OWNED),
        ShelvedBook(listOf("author misc"), "cool title"))
    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val bookrepo = mock<BookRepository>{
        on{getShelvedBooks()}.thenReturn(testShelvedBooks)
    }


    @Test
    fun `load books from Owned Shelf`() = runTest(testDispatcher) {
        val expected = listOf(
        ShelvedBook(listOf("author"), "cool title","isbn", Shelf.OWNED),
        ShelvedBook(listOf("super author"), "cooler title","isbn", Shelf.OWNED))
       val usecase = LoadBooksForShelfUseCase(bookrepo, testDispatcher)
        val books = usecase.invoke(Shelf.OWNED)
        assertEquals(books, expected)

    }
    @Test
    fun `load books from want Shelf`() = runTest(testDispatcher) {
        val expected = listOf( ShelvedBook(listOf("author misc"), "cool title"))
        val usecase = LoadBooksForShelfUseCase(bookrepo, testDispatcher)
        val books = usecase.invoke(Shelf.WANT)
        assertEquals(books, expected)

    }
}