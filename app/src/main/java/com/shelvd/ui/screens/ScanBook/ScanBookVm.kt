package com.shelvd.ui.screens.scanBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.repo.BookRepository
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ScanBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanBookVm @Inject constructor(
    private val scanBookUseCase: ScanBookUseCase,
    private val isbnLookUpUseCase: IsbnLookUpUseCase,
    private val bookRepository: BookRepository
) : ViewModel() {

    private val _state = MutableStateFlow<ScanBookViewState>(ScanBookViewState.AwaitScan)
    val state: StateFlow<ScanBookViewState> = _state

    fun handleIntent(intent: ScanBookIntent) {
        when (intent) {
            is ScanBookIntent.StartScan -> {
                scan()
            }

            is ScanBookIntent.LookUp -> {
                lookUp(intent.isbn)
            }

            is ScanBookIntent.ShelveBook -> {
                shelveBook(intent.book.copy(shelf = intent.shelf))
            }
        }
    }

    private fun shelveBook(book: ShelvedBook) {
        viewModelScope.launch {
            bookRepository.addBookToShelf(book)
        }
    }

    private fun scan() {
        viewModelScope.launch {
            scanBookUseCase.invoke().collect { result ->
                updateUIState(result)

            }
        }
    }

    private fun lookUp(isbn: String) {
        viewModelScope.launch {
            isbnLookUpUseCase.invoke(isbn).collect { result ->
                updateUIState(result)
            }
        }
    }

    private fun updateUIState(book: ShelvedBook?) {
        if (book != null)
            _state.value = ScanBookViewState.BookScanSuccess(book)
        else
            _state.value = ScanBookViewState.BookScanError("Isbn look up failed")
    }

}