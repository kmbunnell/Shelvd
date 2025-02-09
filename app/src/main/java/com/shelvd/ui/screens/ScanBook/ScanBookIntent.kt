package com.shelvd.ui.screens.scanBook

import com.shelvd.data.model.Shelf

sealed class ScanBookIntent {
    object StartScan: ScanBookIntent()
    data class LookUp(val isbn: String): ScanBookIntent()
    data class ShelveBook(val shelf: Shelf): ScanBookIntent()
}