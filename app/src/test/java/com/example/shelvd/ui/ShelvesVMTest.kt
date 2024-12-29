package com.example.shelvd.ui

import com.shelvd.data.repo.DefaultShelfRepository
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

    private val repo = DefaultShelfRepository()
    @OptIn(ExperimentalCoroutinesApi::class)

    @Test
    fun `load shelves`() = runTest(UnconfinedTestDispatcher()) {
        val vm = ShelvesVM(repo)
        vm.handleIntent(ShelvesIntent.getShelvesList)
        assertEquals(vm.state.value, ShelvesViewState.ShelvesList(repo.getShelves()))
        val loadedState = vm.state.value as ShelvesViewState.ShelvesList
        assertTrue(loadedState.shelves.isNotEmpty())
    }
}