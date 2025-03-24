package com.example.shelvd.domain

import com.shelvd.data.Util
import com.shelvd.data.api.ApiService
import com.shelvd.data.model.Edition
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.DefaultBookRepository
import com.shelvd.domain.GetBooksByShelfUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.mockito.kotlin.mock
import org.junit.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

class GetBooksByShelfUseCaseTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    val testDispatcher = UnconfinedTestDispatcher()
    val mockapi = mock<ApiService>()
    val bookrepo = DefaultBookRepository(mockapi)
    val usecase = GetBooksByShelfUseCase(bookrepo)

    @Test
    fun `load books from Owned Shelf`() = runTest(testDispatcher) {
        val expected = listOf(
            ShelvedBook(
                listOf("Sarah J Maas"),
                "A Court of Silver Flames",
                isbn = "1111",
                Shelf.OWNED,
                Util.calculateEditionFlags(listOf(Edition.ARC)),
                "I wish"
            ),
            ShelvedBook(
                listOf("Jay Kristoff"),
                "Empire of the Vampire",
                isbn = "2222",
                Shelf.OWNED,
                Util.calculateEditionFlags(listOf(Edition.SPECIAL, Edition.SIGNED)),
                ""
            ),

            ShelvedBook(
                listOf("Brigid Kemmerer"),
                "Defy the Night",
                isbn = "3333",
                Shelf.OWNED,
                Util.calculateEditionFlags(listOf(Edition.HARDBACK)),
                ""
            ),

            ShelvedBook(
                listOf("Brandon Sanderson"),
                "Mistborn: The final Empire",
                isbn = "4444",
                Shelf.OWNED,
                Util.calculateEditionFlags(listOf(Edition.SPECIAL)),
                "Anniversary Edition"
            )
        )

        val books = usecase.invoke(Shelf.OWNED)
        assertEquals(books, expected)
    }

    @Test
    fun `load books from want Shelf`() = runTest(testDispatcher) {
        val expected = listOf(
            ShelvedBook(
                authors = listOf("Jennifer Armentrout"),
                title = "A Soul of Blood and Ash",
                isbn = "7777",
                shelf = Shelf.WANT
            )
        )
        val books = usecase.invoke(Shelf.WANT)
        assertEquals(books, expected)

    }
}