package com.shelvd.ui.screens.ScanBook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shelvd.domain.ScanBookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanBookVm @Inject constructor(val scanBookUseCase: ScanBookUseCase): ViewModel() {

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
            scanBookUseCase.invoke().collect{
                data -> _state.value = ScanBookViewState.ScannedBookSuccess("Got it")
            }
        }

    }
}