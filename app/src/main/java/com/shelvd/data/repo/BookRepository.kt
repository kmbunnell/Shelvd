package com.shelvd.data.repo

import com.shelvd.data.model.Book
import javax.inject.Inject


interface BookRepository {
     fun getBooks(): List<Book>
}

class DefaultBookRepository @Inject constructor(): BookRepository {
    override fun getBooks(): List<Book> {
       return listOf(
           Book("Sarah J Maas", "A Court of Silver Flames"),
           Book("Jay Kristoff", "Empire of the Vampire"),
           Book("Brigid Kemmerer", "Defy the Night"),
           Book("Brandon Sanderson", "Mistborn: The final Empire"),
       )
    }

}