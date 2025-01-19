package com.shelvd.ui.screens.scanBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.data.model.ApiResult
import com.shelvd.data.model.Book
import com.shelvd.domain.IsbnLookUpUseCase
import com.shelvd.domain.ScanBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanBookVm @Inject constructor(val scanBookUseCase: ScanBookUseCase, val isbnLookupUseCase:IsbnLookUpUseCase): ViewModel() {

    private val _state = MutableStateFlow<ScanBookViewState>(ScanBookViewState.AwaitScan)
    val state: StateFlow<ScanBookViewState> = _state

    fun handleIntent(intent: ScanBookIntent) {
        when (intent) {
            is ScanBookIntent.StartScan -> {
                scan()
            }
        }
    }

    private fun scan() {
        viewModelScope.launch {
           /* scanBookUseCase.invoke().collect{
                data -> _state.value = ScanBookViewState.ScannedBookSuccess("Got it")

            }*/
            isbnLookupUseCase.invoke("9780545522267").collect{
              result -> updateUIState(result)
            }
        }
    }
    private fun updateUIState(result: ApiResult<Book>)
    {
        if(result.data!=null)
            _state.value = ScanBookViewState.BookScanSuccess(result.data)
        else
            _state.value = ScanBookViewState.BookScanError("Isbn look up failed")
    }

}