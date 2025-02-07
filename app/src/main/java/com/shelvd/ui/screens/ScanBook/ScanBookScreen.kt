package com.shelvd.ui.screens.scanBook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.shelvd.R
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.Shelf


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
        

        var selectedIndex by remember { mutableIntStateOf(0) }

        LookUpOptions(selectedIndex){
            selectedIndex = it
        }
        LookUpBy(selectedIndex, onAction)

        HorizontalDivider(color = Color.Blue, thickness = 1.dp)

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
fun ScanButton(onAction: (ScanBookIntent) -> Unit){
    Button(
        onClick = {
            onAction(ScanBookIntent.StartScan)
        }
    ) {
        Text(text = stringResource(R.string.scan))
    }
}

@Composable
fun LookUpBy(selectedIndex: Int, onAction: (ScanBookIntent) -> Unit)
{
    if(selectedIndex==0)
        ScanButton(onAction)
    else
        IsbnLookUpRow(onAction)
}
@Composable
fun LookUpOptions(selectedIndex: Int, onChanged: (Int)-> Unit) {

    val options = listOf("Scan", "Isbn")

    SingleChoiceSegmentedButtonRow {
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
fun IsbnLookUpRow(onAction: (ScanBookIntent) -> Unit) {
    var isbnText by remember { mutableStateOf("9781635577020") }

    Row {
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
fun BookFound(book: BookResult, onAction: (ScanBookIntent) -> Unit) {
    var selectedShelf by remember { mutableStateOf(Shelf.CHECK) }
    Column {
        Text(text = book.docs[0].title)
        Text(text = book.docs[0].authorName[0])
        Row(verticalAlignment = Alignment.CenterVertically) {
            ShelfSelectDropDown(shelf = selectedShelf, onShelfSelection = { selectedShelf = it })
            Button(
                onClick = {
                    onAction(ScanBookIntent.ShelveBook(selectedShelf))
                },
                modifier = Modifier.padding(vertical = 30.dp)
            ) {
                Text(text= stringResource(R.string.shelve))
            }
        }
    }
}

@Composable
fun ShelfSelectDropDown(shelf: Shelf, onShelfSelection: (Shelf) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.shelveOn, shelf.shelfName)
        )
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Shelves")
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
}

@Composable
fun BookNotFound() {
    Text("Could not look up book")
}