package com.shelvd.ui.screens.shelves

import com.shelvd.data.model.Shelf

sealed class ShelvesViewState {
        object Loading: ShelvesViewState()
        data class ShelvesList (val shelves:List<Shelf>): ShelvesViewState()
        data class Error (val message:String): ShelvesViewState()
}