package com.shelvd.ui.screens.shelves

import com.shelvd.data.model.Shelf

sealed class ShelvesIntent {
    data class LoadBooks(val shelf: Shelf):ShelvesIntent()

}