package com.shelvd.ui.screens.shelves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.data.model.Shelf
import com.shelvd.data.repo.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShelvesVM @Inject constructor(
    val bookRepository: BookRepository
) : ViewModel() {
    private val _state = MutableStateFlow<ShelvesViewState>(ShelvesViewState.Loading)
    val state: StateFlow<ShelvesViewState> = _state

    init {
        handleIntent(ShelvesIntent.LoadBooks(Shelf.OWNED))
    }

    fun handleIntent(intent: ShelvesIntent) {
        when (intent) {
            is ShelvesIntent.LoadBooks -> {
                loadBooks(intent.shelf)
            }
        }
    }

    private fun loadBooks(shelf: Shelf) {
        _state.value = ShelvesViewState.Loading
        viewModelScope.launch {
            _state.value = try {
                ShelvesViewState.ShelvedBooks(bookRepository.getShelvedBooksByShelf(shelf))
            } catch (e: Exception) {
                ShelvesViewState.Error("Wrong")
            }
        }
    }
}