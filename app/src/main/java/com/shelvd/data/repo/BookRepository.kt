package com.shelvd.data.repo

import com.shelvd.data.model.Book
import com.shelvd.data.model.Shelf
import javax.inject.Inject


interface BookRepository {
     fun getBooks(): List<Book>
     fun addBook()
     fun removeBook(idx:Int)
}

class DefaultBookRepository @Inject constructor(): BookRepository {
    private val bookList: MutableList<Book> = mutableListOf()

    override fun getBooks(): List<Book> {
      if(bookList.size == 0)
      {
          bookList.add(Book("Sarah J Maas", "A Court of Silver Flames", Shelf.OWNED))
          bookList.add(Book("Jay Kristoff", "Empire of the Vampire", Shelf.OWNED))
          bookList.add(Book("Brigid Kemmerer", "Defy the Night", Shelf.OWNED))
          bookList.add(Book("Brandon Sanderson", "Mistborn: The final Empire", Shelf.OWNED))
          bookList.add(Book("Brigid Kemmerer", "Carving Shadows Into Gold", Shelf.PREORDERED))
          bookList.add(Book("Brynne Weaver", "Scythe and Sparrow", Shelf.PREORDERED))
      }
        return bookList.toList()
    }

    override fun addBook() {
       bookList.add(Book("VE Schwab", "A Darker Shade of Magic"))
    }

    override fun removeBook(idx: Int) {
       bookList.removeAt(idx)
    }

}