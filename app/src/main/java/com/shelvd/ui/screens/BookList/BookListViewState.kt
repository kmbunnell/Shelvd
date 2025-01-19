package com.shelvd.ui.screens.BookList

import com.shelvd.data.model.ShelvedBook

sealed class BookListViewState{
    object Loading: BookListViewState()
    data class BooksLoaded (val shelvedBooks:List<ShelvedBook>): BookListViewState()
    data class Error (val message:String): BookListViewState()
}
