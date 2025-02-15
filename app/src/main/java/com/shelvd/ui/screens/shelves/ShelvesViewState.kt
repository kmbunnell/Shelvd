package com.shelvd.ui.screens.shelves


import com.shelvd.data.model.ShelvedBook

sealed class ShelvesViewState {
    object Loading : ShelvesViewState()
    data class ShelvedBooks(val shelvedBooks: List<ShelvedBook>) : ShelvesViewState()
    data class Error(val message: String) : ShelvesViewState()
}
