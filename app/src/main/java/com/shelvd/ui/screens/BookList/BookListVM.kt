package com.shelvd.ui.screens.bookList

import androidx.lifecycle.ViewModel
import com.shelvd.data.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class BookListVM @Inject constructor(private val repository: BookRepository): ViewModel()  {
    private val _state = MutableStateFlow<BookListViewState>(BookListViewState.Loading)
    val state: StateFlow<BookListViewState> = _state

    init {
        handleIntent(BookIntent.LoadBooks)
    }

    fun handleIntent(intent: BookIntent) {
        when (intent) {
            BookIntent.LoadBooks -> getBookList()
            BookIntent.AddBook -> addBook()
            is BookIntent.RemoveBook -> removeBook(intent.idx)
        }
    }

    private fun getBookList() {
        _state.value = BookListViewState.Loading
        _state.value = try {
            val books = repository.getBooks()
            BookListViewState.BooksLoaded(books)
        } catch (e: Exception) {
            BookListViewState.Error("Wrong")
        }
    }

    private fun addBook() {
        repository.addBook()
        getBookList()
    }

    private fun removeBook(idx: Int) {
        if (idx != -1) {
            repository.removeBook(idx)
            getBookList()
        }
    }
}