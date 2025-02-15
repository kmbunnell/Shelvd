package com.shelvd.ui.screens.scanBook

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shelvd.R
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook


@Composable
fun ScanBookRoute(viewModel: ScanBookVm = hiltViewModel()) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ScanBookScreen(currentState, viewModel::handleIntent)

}

@Composable
fun ScanBookScreen(state: ScanBookViewState, onAction: (ScanBookIntent) -> Unit) {

    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {

        Column (horizontalAlignment = Alignment.CenterHorizontally) {

            var selectedIndex by remember { mutableIntStateOf(0) }
            LookUpOptions(modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp), selectedIndex) {
                selectedIndex = it
            }

            LookUpBy(modifier = Modifier.padding(bottom = 10.dp),
                selectedIndex=  selectedIndex,
                onAction= onAction)

            HorizontalDivider(color = Color.Blue, thickness = 1.dp)
        }

        when (state) {
            is ScanBookViewState.BookScanSuccess -> {
                BookFound(state.book, onAction = onAction)
            }

            is ScanBookViewState.BookScanError -> {
                BookNotFound()
            }

            else -> {}
        }
    }

}
@Composable
fun ScanButton(modifier: Modifier = Modifier, onAction: (ScanBookIntent) -> Unit){
    Button(modifier = modifier.width(200.dp),
        onClick = {
            onAction(ScanBookIntent.StartScan)
        }
    ) {
        Text(text = stringResource(R.string.scan))
    }
}

@Composable
fun LookUpBy(modifier: Modifier, selectedIndex: Int, onAction: (ScanBookIntent) -> Unit)
{
    if (selectedIndex == 0)
        ScanButton(modifier = modifier, onAction = onAction)
    else
        IsbnLookUpRow(modifier = modifier, onAction  = onAction)

}
@Composable
fun LookUpOptions(modifier: Modifier = Modifier, selectedIndex: Int, onChanged: (Int)-> Unit) {

    val options = listOf(stringResource(R.string.scan), stringResource(R.string.isbn))

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onChanged(index) },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

@Composable
fun IsbnLookUpRow(modifier: Modifier = Modifier, onAction: (ScanBookIntent) -> Unit) {
    var isbnText by remember { mutableStateOf("9781635577020") }

    Row(modifier = modifier) {
        TextField(
            modifier = Modifier.padding(horizontal = 5.dp),
            value = isbnText,
            onValueChange = { isbnText = it },
            label = { Text(text = stringResource(R.string.isbn)) }
        )

        Button(
            onClick = {
                onAction(ScanBookIntent.LookUp(isbnText))
            }
        ) {
            Text(text = stringResource(R.string.find))
        }
    }
}

@Composable
fun BookFound(book: ShelvedBook, onAction: (ScanBookIntent) -> Unit) {
    var selectedShelf by remember { mutableStateOf(book.shelf) }
    Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
        Text(text = book.title)
        Text(text = book.authors[0])
        Row( verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.padding(end = 10.dp),
                text = stringResource(R.string.shelveOn)
            )
            ShelfSelectDropDown(selectedShelf, onShelfSelection = { selectedShelf = it })

        }
        Row(modifier = Modifier.align(Alignment.End)) {
            Button(
                onClick = {
                    onAction(ScanBookIntent.ShelveBook(book, selectedShelf))
                },
            ) {
                Text(text = stringResource(R.string.shelve))
            }
        }
    }
}

@Composable
fun ShelfSelectDropDown(selectedShelf:Shelf, onShelfSelection: (Shelf) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

        Text(
            text = selectedShelf.shelfName
        )
        IconButton(
            onClick = { expanded = !expanded }) {
            Icon(Icons.Filled.ArrowDropDown, contentDescription = "Shelves")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Shelf.entries.forEach { shelf ->
                DropdownMenuItem(
                    onClick = {
                        onShelfSelection(shelf)
                        expanded = false
                    },
                    text = {
                        Text(text = shelf.shelfName)
                    }
                )
            }
        }
}

@Composable
fun BookNotFound() {
    Text("Could not look up book")
}