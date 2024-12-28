package com.shelvd.data.repo

import com.shelvd.data.model.Book
import javax.inject.Inject


interface BookRepository {
     fun getBooks(): List<Book>
     fun addBook()
}

class DefaultBookRepository @Inject constructor(): BookRepository {
    private val bookList: MutableList<Book> = mutableListOf()

    override fun getBooks(): List<Book> {
      if(bookList.size == 0)
      {
          bookList.add(Book("Sarah J Maas", "A Court of Silver Flames"))
          bookList.add(Book("Jay Kristoff", "Empire of the Vampire"))
          bookList.add(Book("Brigid Kemmerer", "Defy the Night"))
          bookList.add(Book("Brandon Sanderson", "Mistborn: The final Empire"))
      }
        return bookList.toList()
    }

    override fun addBook() {
       bookList.add(Book("VE Schwab", "A Darker Shade of Magic"))
    }
}