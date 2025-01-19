package com.shelvd.ui.screens.bookList

sealed class BookIntent {
    object LoadBooks: BookIntent()
    object AddBook: BookIntent()
    data class RemoveBook(val idx: Int): BookIntent()
}