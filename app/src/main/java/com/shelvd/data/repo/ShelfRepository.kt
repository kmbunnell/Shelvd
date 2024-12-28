package com.shelvd.data.repo

import com.shelvd.data.model.Shelf
import javax.inject.Inject

interface ShelfRepository {
    fun getShelves(): List<Shelf>
    fun addShelf(title:String)
    fun removeShelf(idx:Int)
}


class DefaultShelfRepository @Inject constructor(): ShelfRepository {

    private val shelfList: MutableList<Shelf> = mutableListOf()

    override fun getShelves(): List<Shelf> {
        if(shelfList.size == 0) {
            shelfList.add(Shelf("Owned", 1))
        }
        return shelfList.toList()
    }

    override fun addShelf(title:String) {
        shelfList.add(Shelf("title", shelfList.size+1))
    }

    override fun removeShelf(idx: Int) {
        shelfList.removeAt(idx)
    }
}