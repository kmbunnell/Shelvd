package com.shelvd.data.repo

import com.shelvd.data.api.ApiService
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


interface BookRepository {
     fun getShelvedBooks(): List<ShelvedBook>
     fun addBookToShelf()
     fun removeBookFromShelf(idx:Int)
     fun lookUpBookByISBN(isbn:String):Flow<ShelvedBook?>
}

class DefaultBookRepository @Inject constructor(val apiService: ApiService): BookRepository {
    private val shelvedBookList: MutableList<ShelvedBook> = mutableListOf()

    override fun getShelvedBooks(): List<ShelvedBook> {
      if(shelvedBookList.size == 0)
      {
          shelvedBookList.add(ShelvedBook(listOf("Sarah J Maas"), "A Court of Silver Flames", isbn="",Shelf.OWNED))
          shelvedBookList.add(ShelvedBook(listOf("Jay Kristoff"), "Empire of the Vampire", isbn="", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook(listOf("Brigid Kemmerer"), "Defy the Night",isbn="", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook(listOf("Brandon Sanderson"), "Mistborn: The final Empire",isbn="", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook(listOf("Brigid Kemmerer"), "Carving Shadows Into Gold", isbn="", Shelf.PREORDERED))
          shelvedBookList.add(ShelvedBook(listOf("Brynne Weaver"), "Scythe and Sparrow",isbn="", Shelf.PREORDERED))
          shelvedBookList.add(ShelvedBook(listOf("Jennifer Armentrout"), "A Soul of Blood and Ash",isbn="", Shelf.WANT))
      }
        return shelvedBookList.toList()
    }

    override fun addBookToShelf() {
       shelvedBookList.add(ShelvedBook(listOf("VE Schwab"), "A Darker Shade of Magic"))
    }

    override fun removeBookFromShelf(idx: Int) {
       shelvedBookList.removeAt(idx)
    }

    override fun lookUpBookByISBN(isbn: String): Flow<ShelvedBook?> = apiService.getBookByIsbn(isbn).map{
        it.data?.let { book->
          return@map ShelvedBook(authors = book.docs[0].authorName, title= book.docs[0].title, isbn = isbn)
        }
    }



}