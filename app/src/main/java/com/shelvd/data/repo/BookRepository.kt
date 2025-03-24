package com.shelvd.data.repo

import com.shelvd.data.Util
import com.shelvd.data.api.ApiService
import com.shelvd.data.model.Edition
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


interface BookRepository {
    fun addBookToShelf(newBook: ShelvedBook)
    fun checkForDuplicate(newBook: ShelvedBook): ShelvedBook?
    fun removeBookFromShelf(book: ShelvedBook)
    fun lookUpBookByISBN(isbn: String): Flow<ShelvedBook?>
    fun loadShelvedBooks(): List<ShelvedBook>
    fun updateBookShelf(book: ShelvedBook)
}

class DefaultBookRepository @Inject constructor(
    val apiService: ApiService,
) : BookRepository {
    private val shelvedBookList: MutableList<ShelvedBook> = mutableListOf()

    override fun loadShelvedBooks(): List<ShelvedBook> {
        if (shelvedBookList.size == 0)
            createBookData()

        return shelvedBookList.toList()
    }

    override fun addBookToShelf(newBook: ShelvedBook) {
        shelvedBookList.add(newBook)
    }

    override fun updateBookShelf(book: ShelvedBook) {
        shelvedBookList.find { it.isbn == book.isbn }
            ?.let { oldBook -> shelvedBookList[shelvedBookList.indexOf(oldBook)] = book }
    }

    override fun removeBookFromShelf(book: ShelvedBook) {
        shelvedBookList.remove(book)
    }

    override fun lookUpBookByISBN(isbn: String): Flow<ShelvedBook?> =
        apiService.getBookByIsbn(isbn).map {
            it.data?.let { book ->
                return@map ShelvedBook(
                    authors = book.docs[0].authorName,
                    title = book.docs[0].title,
                    isbn = isbn
                )
            }
        }

    override fun checkForDuplicate(newBook: ShelvedBook): ShelvedBook? =
        shelvedBookList.find { it.isbn == newBook.isbn }

    private fun createBookData() {
        val book1 = ShelvedBook(
            listOf("Sarah J Maas"),
            "A Court of Silver Flames",
            isbn = "1111",
            Shelf.OWNED,
            Util.calculateEditionFlags(listOf(Edition.ARC)),
            "I wish"
        )
        shelvedBookList.add(
            book1
        )

        val book2 = ShelvedBook(
            listOf("Jay Kristoff"),
            "Empire of the Vampire",
            isbn = "2222",
            Shelf.OWNED,
            Util.calculateEditionFlags(listOf(Edition.SPECIAL, Edition.SIGNED)),
            ""
        )
        shelvedBookList.add(
            book2
        )

        val book3 = ShelvedBook(
            listOf("Brigid Kemmerer"),
            "Defy the Night",
            isbn = "3333",
            Shelf.OWNED,
            Util.calculateEditionFlags(listOf(Edition.HARDBACK)),
            ""
        )
        shelvedBookList.add(
            book3
        )

        val book4 = ShelvedBook(
            listOf("Brandon Sanderson"),
            "Mistborn: The final Empire",
            isbn = "4444",
            Shelf.OWNED,
            Util.calculateEditionFlags(listOf(Edition.SPECIAL)),
            "Anniversary Edition"
        )
        shelvedBookList.add(
            book4
        )

        val book5 = ShelvedBook(
            listOf("Brigid Kemmerer"),
            "Carving Shadows Into Gold",
            isbn = "5555",
            Shelf.PREORDERED,
            Util.calculateEditionFlags(listOf(Edition.HARDBACK)),
            "B&N Jan 28"
        )
        shelvedBookList.add(
            book5
        )

        val book6 = ShelvedBook(
            listOf("Brynne Weaver"),
            "Scythe and Sparrow",
            isbn = "6666",
            Shelf.PREORDERED
        )
        shelvedBookList.add(
            book6
        )

        val book7 = ShelvedBook(
            listOf("Jennifer Armentrout"),
            "A Soul of Blood and Ash",
            isbn = "7777",
            Shelf.WANT
        )
        shelvedBookList.add(
            book7
        )
    }

}