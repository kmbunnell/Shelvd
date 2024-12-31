package com.shelvd.ui.screens.shelves


import com.shelvd.data.model.Book

sealed class ShelvesViewState {
        object Loading: ShelvesViewState()
        data class ShelvedBooks (val books:List<Book>): ShelvesViewState()
        data class Error (val message:String): ShelvesViewState()
}
