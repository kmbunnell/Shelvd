package com.example.shelvd.ui

import com.example.shelvd.data.model.Book

data class BookViewState(
    val loading: Boolean = false,
    val books: List<Book> = emptyList(),
    val error: String? = null
)
