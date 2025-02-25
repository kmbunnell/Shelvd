package com.shelvd.ui.screens.shelves

import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook

sealed class ShelvesIntent {
    data class LoadBooks (val shelf: Shelf) : ShelvesIntent()
    data class MoveBook (val book: ShelvedBook, val shelf: Shelf): ShelvesIntent()
    data class DeleteBook (val book: ShelvedBook): ShelvesIntent()

}