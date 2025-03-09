package com.shelvd.ui.screens.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.shelvd.R

import com.shelvd.data.model.Shelf

@Composable
fun ShelfDropDownRow(selectedShelf: Shelf, onShelfSelection: (Shelf) -> Unit, onShelveClick: ()->Unit)
{
    Row(verticalAlignment = Alignment.CenterVertically){
        Text(
            modifier = Modifier.padding(end = 10.dp),
            text = stringResource(R.string.shelveOn),
        )
        ShelfDropDown(selectedShelf, onShelfSelection)
        Button(
            onClick = onShelveClick
        ) {
            Text(text = stringResource(R.string.shelve))
        }
    }
}
@Composable
fun ShelfDropDown(selectedShelf: Shelf, onShelfSelection: (Shelf) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Text(text = selectedShelf.shelfName)
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