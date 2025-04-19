package com.shelvd.ui.screens.scanBook

import com.shelvd.data.model.ShelvedBook

sealed class ScanBookViewState {
    object AwaitScan : ScanBookViewState()
    object Scanning: ScanBookViewState()
    data class BookScanSuccess(val book: ShelvedBook, val isDup: Boolean) : ScanBookViewState()
    data class BookScanError(val message: String) : ScanBookViewState()
}