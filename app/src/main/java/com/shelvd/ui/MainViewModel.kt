package com.shelvd.ui

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


    fun handleIntent(intent: BookIntent) {
            when (intent) {
                is BookIntent.LoadBooks -> getBookList()
            }

    }

    private fun getBookList() {
        _state.value = BookListViewState.Loading

        _state.value = try {
            BookListViewState.BooksLoaded(repository.getBooks())
        } catch (e: Exception) {
            BookListViewState.Error("Wrong")

        }
    }

}