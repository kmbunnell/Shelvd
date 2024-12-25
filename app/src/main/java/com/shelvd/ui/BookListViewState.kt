package com.shelvd.ui

import com.shelvd.data.model.Book

sealed class BookListViewState{
    object Loading: BookListViewState()
    data class BooksLoaded (val books:List<Book>): BookListViewState()
    data class Error (val message:String): BookListViewState()
}
