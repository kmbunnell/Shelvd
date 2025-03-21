package com.shelvd.ui.screens.shelves


import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook

sealed class ShelvesViewState {
    object Loading : ShelvesViewState()
    data class ShelvedBooks(val currentShelf: Shelf, val shelvedBooks: List<ShelvedBook>) : ShelvesViewState()
    data class Error(val message: String) : ShelvesViewState()
}
