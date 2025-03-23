package com.shelvd.data.model

import java.util.EnumSet


data class ShelvedBook(
    val authors: List<String>,
    val title: String,
    val isbn: String = "",
    val shelf: Shelf = Shelf.WANT,
){
    val editionSet: EnumSet<Edition> = EnumSet.noneOf(Edition::class.java)
    var _notes: String=""


    @Synchronized
    fun addEdition(edition: Edition) {
        editionSet.add(edition)
    }

    @Synchronized
    fun removeEdition(edition: Edition) {
        editionSet.remove(edition)
    }

    @Synchronized
    fun getEditions(): Set<Edition> {
        return editionSet.toSet()
    }

}



