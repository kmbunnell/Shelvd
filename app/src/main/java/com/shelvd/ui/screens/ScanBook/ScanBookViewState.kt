package com.shelvd.ui.screens.ScanBook

sealed class ScanBookViewState {
    object AwaitScan: ScanBookViewState()
    data class ScannedBookSuccess(val isbn: String): ScanBookViewState()
    data class ScannedBookError(val message: String): ScanBookViewState()
}