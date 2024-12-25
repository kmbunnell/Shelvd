package com.example.shelvd.ui

import androidx.lifecycle.ViewModel
import com.example.shelvd.data.repo.DefaultBookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel(private val repository: DefaultBookRepository): ViewModel() {
    private val _state = MutableStateFlow(BookViewState())
    val state: StateFlow<BookViewState> = _state


    fun handleIntent(intent: BookIntent) {
            when (intent) {
                is BookIntent.LoadBooks -> getBookList()
            }

    }

    private fun getBookList() {
        _state.value = _state.value.copy(loading = true, error = null)

        try {
            val books = repository.getBooks()
            _state.value = BookViewState(loading = false, books = books)
        } catch (e: Exception) {
            _state.value =
                BookViewState(loading = false, error = e.message ?: "Error getting books")
        }
    }

}