package com.shelvd.ui.screens.scanBook

import com.shelvd.data.model.Book

sealed class ScanBookViewState {
    object AwaitScan: ScanBookViewState()
    data class BookScanSuccess(val book: Book): ScanBookViewState()
    data class BookScanError(val message: String): ScanBookViewState()
}