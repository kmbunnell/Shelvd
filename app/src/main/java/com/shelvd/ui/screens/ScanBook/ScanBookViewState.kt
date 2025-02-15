package com.shelvd.ui.screens.scanBook

import com.shelvd.data.model.ShelvedBook

sealed class ScanBookViewState {
    object AwaitScan : ScanBookViewState()
    data class Success(val s: String) : ScanBookViewState()
    data class BookScanSuccess(val book: ShelvedBook) : ScanBookViewState()
    data class BookScanError(val message: String) : ScanBookViewState()
}