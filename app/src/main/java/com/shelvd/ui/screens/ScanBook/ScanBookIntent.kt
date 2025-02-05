package com.shelvd.ui.screens.scanBook

sealed class ScanBookIntent {
    object StartScan: ScanBookIntent()
    data class LookUp(val isbn: String): ScanBookIntent()
}