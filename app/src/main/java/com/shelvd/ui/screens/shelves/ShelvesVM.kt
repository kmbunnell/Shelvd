package com.shelvd.ui.screens.shelves

import androidx.lifecycle.ViewModel
import com.shelvd.data.repo.BookRepository
import com.shelvd.data.repo.ShelfRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ShelvesVM @Inject constructor(private val shelvesRepo:ShelfRepository): ViewModel() {
    private val _state = MutableStateFlow<ShelvesViewState>(ShelvesViewState.Loading)
    val state: StateFlow<ShelvesViewState> = _state


    init {
        handleIntent(ShelvesIntent.getShelvesList)
    }

    fun handleIntent(intent: ShelvesIntent) {
        when (intent) {
            ShelvesIntent.getShelvesList -> loadShelfList()
        }
    }

    private fun loadShelfList()
    {
        _state.value = ShelvesViewState.Loading
        _state.value = try {
            val shelves = shelvesRepo.getShelves()
            ShelvesViewState.ShelvesList(shelves)
        } catch (e: Exception) {
            ShelvesViewState.Error("Wrong")
        }
    }

}