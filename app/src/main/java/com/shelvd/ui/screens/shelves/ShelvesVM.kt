package com.shelvd.ui.screens.shelves

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.DeleteBookUseCase
import com.shelvd.domain.LoadBooksByShelfUseCase
import com.shelvd.domain.MoveBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShelvesVM @Inject constructor(
   val loadBooksByShelfUseCase: LoadBooksByShelfUseCase,
    val moveBookUseCase: MoveBookUseCase,
    val deleteBookUseCase: DeleteBookUseCase

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
            is ShelvesIntent.MoveBook->{}
            is ShelvesIntent.DeleteBook->{}
        }
    }

    private fun loadBooks(shelf: Shelf) {
        _state.value = ShelvesViewState.Loading
        viewModelScope.launch {
            _state.value = try {
                ShelvesViewState.ShelvedBooks(loadBooksByShelfUseCase.invoke(shelf))
            } catch (e: Exception) {
                ShelvesViewState.Error("Wrong")
            }
        }
    }

    private fun moveBook(book:ShelvedBook, shelf:Shelf)
    {
        moveBookUseCase.invoke(book, shelf)
    }

    private fun deleteBook(book:ShelvedBook)
    {
        deleteBookUseCase.invoke(book)
    }
}
//created intents and usecases - need to figure out ui