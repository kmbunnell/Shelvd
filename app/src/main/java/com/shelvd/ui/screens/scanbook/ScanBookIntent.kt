package com.shelvd.ui.screens.scanBook

import com.shelvd.data.model.Edition
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook

sealed class ScanBookIntent {
    object StartScan : ScanBookIntent()
    data class LookUp(val isbn: String) : ScanBookIntent()
    data class ShelveBook(val book: ShelvedBook, val shelf: Shelf, val editionFlags: List<Edition>) : ScanBookIntent()
    object ResetScreen: ScanBookIntent()
}