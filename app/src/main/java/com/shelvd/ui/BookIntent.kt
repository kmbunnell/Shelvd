package com.shelvd.ui

sealed class BookIntent {
    object LoadBooks: BookIntent()
    object AddBook: BookIntent()
    object RemoveBook: BookIntent()
}