package com.shelvd.ui.screens.scanBook

import com.shelvd.data.model.BookResult

sealed class ScanBookViewState {
    object AwaitScan: ScanBookViewState()
    data class BookScanSuccess(val book: BookResult): ScanBookViewState()
    data class BookScanError(val message: String): ScanBookViewState()
}