package com.shelvd.ui.screens.ScanBook

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ScannBookRoute( viewModel: ScanBookVm = hiltViewModel() )
{
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ScanBookScreen(currentState, viewModel::handleIntent)

}
@Composable
fun ScanBookScreen( state: ScanBookViewState, onAction: (ScanBookIntent) -> Unit ){

    Button(onClick = {
        onAction(ScanBookIntent.StartScan)
    },
        modifier = Modifier.padding(vertical = 60.dp)){
        Text("Scan")
    }

}