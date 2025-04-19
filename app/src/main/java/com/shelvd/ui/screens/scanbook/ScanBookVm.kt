package com.shelvd.ui.screens.scanBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.data.model.Edition
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ScanBookUseCase
import com.shelvd.domain.ShelveBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanBookVm @Inject constructor(
    private val scanBookUseCase: ScanBookUseCase,
    private val isbnLookUpUseCase: IsbnLookUpUseCase,
    private val shelveBookUseCase: ShelveBookUseCase,
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
                shelveBook(intent.book, intent.shelf, intent.editionFlags)
            }

            ScanBookIntent.ResetScreen -> {
                reset()
            }

            else -> {}
        }
    }

    private fun scan() {
        _state.value = ScanBookViewState.Scanning
        viewModelScope.launch {
            scanBookUseCase.invoke().collect { result ->
                bookLookUpResult(result)

            }
        }
    }

    private fun lookUp(isbn: String) {
        _state.value = ScanBookViewState.Scanning
        viewModelScope.launch{
            isbnLookUpUseCase.invoke(isbn).collect { result ->
                bookLookUpResult(result)
            }
        }
    }

    private fun bookLookUpResult(lookUpResult: Pair<ShelvedBook?, Boolean>) {
        lookUpResult.first?.let { book ->
            _state.value = ScanBookViewState.BookScanSuccess(book, lookUpResult.second)

        } ?: setErrorState()
    }

    private fun setErrorState() {
        _state.value = ScanBookViewState.BookScanError("Look up failed")
    }

    private fun shelveBook(book: ShelvedBook, shelf: Shelf, editionFlags: List<Edition>) {

        shelveBookUseCase.invoke(book, shelf, editionFlags)
        reset()
    }

    private fun reset() {
        _state.value = ScanBookViewState.AwaitScan
    }
}