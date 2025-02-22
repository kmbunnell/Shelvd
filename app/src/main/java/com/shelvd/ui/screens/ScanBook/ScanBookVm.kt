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
             ScanBookIntent.StartScan -> {
                scan()
            }

            is ScanBookIntent.LookUp -> {
                lookUp(intent.isbn)
            }

            is ScanBookIntent.ShelveBook -> {
                shelveBook(intent.book.copy(shelf = intent.shelf))
            }

            ScanBookIntent.ResetScreen->{ reset() }
        }
    }

    private fun scan() {
        viewModelScope.launch {
            scanBookUseCase.invoke().collect { result ->
                bookLookUpResult(result)

            }
        }
    }

    private fun lookUp(isbn: String) {
        viewModelScope.launch {
            isbnLookUpUseCase.invoke(isbn).collect { result ->
                bookLookUpResult(result)
            }
        }
    }

    private fun bookLookUpResult(lookUpResult:Pair<ShelvedBook?, Boolean>) {
        lookUpResult.first?.let { book->
            _state.value = ScanBookViewState.BookScanSuccess(book, lookUpResult.second)

        }?:setErrorState()
    }

    private fun setErrorState()
    {
        _state.value = ScanBookViewState.BookScanError("Look up failed")
    }

    private fun shelveBook(book: ShelvedBook) {
        bookRepository.addBookToShelf(book)
        reset()
    }

    private fun reset()
    {
        _state.value = ScanBookViewState.AwaitScan
    }
}