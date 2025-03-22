package com.shelvd.ui.screens.shelves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.domain.DeleteBookUseCase
import com.shelvd.domain.GetBooksByShelfUseCase
import com.shelvd.domain.ReShelveBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShelvesVM @Inject constructor(
    private val booksByShelfUseCase: GetBooksByShelfUseCase,
    private val reshelveBookUseCase: ReShelveBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<ShelvesViewState>(ShelvesViewState.Loading)
    val state: StateFlow<ShelvesViewState> = _state

    fun handleIntent(intent: ShelvesIntent) {
        when (intent) {
            is ShelvesIntent.LoadBooks -> {
                loadBooks(intent.shelf)
            }
            is ShelvesIntent.DeleteBook -> deleteBook(intent.book)
            is ShelvesIntent.ReshelveBook -> {
                reShelve(intent.book, intent.newShelf)
            }
        }
    }

    private fun loadBooks(shelf: Shelf) {
        _state.value = ShelvesViewState.Loading
        viewModelScope.launch {
            _state.value = try {
                ShelvesViewState.ShelvedBooks(shelf, booksByShelfUseCase.invoke(shelf))
            } catch (e: Exception) {
                ShelvesViewState.Error("Wrong")
            }
        }
    }

    private fun reShelve(book: ShelvedBook, shelf: Shelf) {
        reshelveBookUseCase.invoke(book, shelf)
        reloadShelf()
    }

    private fun deleteBook(book: ShelvedBook) {
        deleteBookUseCase.invoke(book)
        reloadShelf()
    }

    private fun reloadShelf() {
        loadBooks(getCurrentShelf())
    }

    private fun getCurrentShelf() =
      when(val currentState =_state.value)
       {
          is ShelvesViewState.ShelvedBooks->{currentState.currentShelf}
          else->Shelf.OWNED
       }
}