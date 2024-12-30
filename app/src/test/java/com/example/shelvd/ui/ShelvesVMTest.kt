package com.example.shelvd.ui

import com.shelvd.data.model.Shelf
import com.shelvd.ui.screens.shelves.ShelvesIntent
import com.shelvd.ui.screens.shelves.ShelvesVM
import com.shelvd.ui.screens.shelves.ShelvesViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ShelvesVMTest {


    @OptIn(ExperimentalCoroutinesApi::class)

    @Test
    fun `load shelves`() = runTest(UnconfinedTestDispatcher()) {
        val vm = ShelvesVM()
        vm.handleIntent(ShelvesIntent.getShelvesList)
        assertEquals(vm.state.value, ShelvesViewState.ShelvesList(shelves = Shelf.entries))
        val loadedState = vm.state.value as ShelvesViewState.ShelvesList
        assertTrue(loadedState.shelves.isNotEmpty())
    }
}