package com.example.shelvd.ui

sealed class BookIntent {
    object LoadBooks: BookIntent()
}