package com.shelvd.ui.screens.scanBook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shelvd.R
import com.shelvd.data.model.Shelf
import com.shelvd.data.model.ShelvedBook

@Composable
fun BookFoundScreen(book: ShelvedBook, isDup: Boolean, onAction: (ScanBookIntent) -> Unit) {
    var selectedShelf by remember { mutableStateOf(book.shelf) }
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        BookInfo(book)
        if (isDup) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = stringResource(R.string.dupDetected, book.title, book.shelf),
                    color = Color.Red
                )
            }
        } else {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = stringResource(R.string.shelveOn),
                )
                ShelfSelectDropDown(selectedShelf, onShelfSelection = { selectedShelf = it })

            }
        }
        Row(modifier = Modifier.align(Alignment.End)) {
            if (isDup)
                ConfirmButton(onAction)
            else
                ShelveButton(book = book, selectedShelf = selectedShelf, onAction = onAction)

        }
    }
}

@Composable
fun ConfirmButton(onAction: (ScanBookIntent) -> Unit) {
    Button(
        onClick = {
            onAction(ScanBookIntent.ResetScreen)
        },
    ) {
        Text(text = stringResource(R.string.ok))
    }
}

@Composable
fun ShelveButton(book: ShelvedBook, selectedShelf: Shelf, onAction: (ScanBookIntent) -> Unit) {
    Button(
        onClick = {
            onAction(ScanBookIntent.ShelveBook(book, selectedShelf))
        },
    ) {
        Text(text = stringResource(R.string.shelve))
    }
}

@Composable
fun ScanButton(modifier: Modifier = Modifier, onAction: (ScanBookIntent) -> Unit) {
    Button(modifier = modifier.width(200.dp),
        onClick = {
            onAction(ScanBookIntent.StartScan)
        }
    ) {
        Text(text = stringResource(R.string.scan))
    }
}

@Composable
fun ShelfSelectDropDown(selectedShelf: Shelf, onShelfSelection: (Shelf) -> Unit) {
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