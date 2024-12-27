package com.shelvd.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.shelvd.data.repo.DefaultBookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: DefaultBookRepository): ViewModel() {
    private val _state = MutableStateFlow<BookListViewState>(BookListViewState.Loading)
    val state: StateFlow<BookListViewState> = _state


    init{
        handleIntent(BookIntent.LoadBooks)
    }

    fun handleIntent(intent: BookIntent) {
            when (intent) {
                is BookIntent.LoadBooks -> getBookList()
                BookIntent.AddBook -> addBook()
                BookIntent.RemoveBook -> TODO()
            }

    }

    private fun getBookList() {

        _state.value = BookListViewState.Loading
        _state.value = try {
            val books = repository.getBooks()
            Log.d("TST", "getBookList: ${books.size}")
            BookListViewState.BooksLoaded(books)
        } catch (e: Exception) {
            BookListViewState.Error("Wrong")

        }
    }
    private fun addBook(){
        Log.d("TST", "addBook")
        repository.addBook()
        getBookList()
    }
}