package com.shelvd.data.repo

import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import javax.inject.Inject


interface BookRepository {
     fun getBooks(): List<ShelvedBook>
     fun addBook()
     fun removeBook(idx:Int)
}

class DefaultBookRepository @Inject constructor(): BookRepository {
    private val shelvedBookList: MutableList<ShelvedBook> = mutableListOf()

    override fun getBooks(): List<ShelvedBook> {
      if(shelvedBookList.size == 0)
      {
          shelvedBookList.add(ShelvedBook("Sarah J Maas", "A Court of Silver Flames", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook("Jay Kristoff", "Empire of the Vampire", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook("Brigid Kemmerer", "Defy the Night", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook("Brandon Sanderson", "Mistborn: The final Empire", Shelf.OWNED))
          shelvedBookList.add(ShelvedBook("Brigid Kemmerer", "Carving Shadows Into Gold", Shelf.PREORDERED))
          shelvedBookList.add(ShelvedBook("Brynne Weaver", "Scythe and Sparrow", Shelf.PREORDERED))
          shelvedBookList.add(ShelvedBook("Jennifer Armentrout", "A Soul of Blood and Ash", Shelf.WANT))
      }
        return shelvedBookList.toList()
    }

    override fun addBook() {
       shelvedBookList.add(ShelvedBook("VE Schwab", "A Darker Shade of Magic"))
    }

    override fun removeBook(idx: Int) {
       shelvedBookList.removeAt(idx)
    }

}