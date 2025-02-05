package com.shelvd.ui.screens.scanBook

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelvd.data.model.BookResult
import com.shelvd.data.model.Shelf

@Composable
fun ScannBookRoute( viewModel: ScanBookVm = hiltViewModel() )
{
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ScanBookScreen(currentState, viewModel::handleIntent)

}
@Composable
fun ScanBookScreen( state: ScanBookViewState, onAction: (ScanBookIntent) -> Unit ){
    var isbnText by remember { mutableStateOf("9781635577020") }
  Column {
      Row{
          TextField(
              value = isbnText,
              onValueChange = { isbnText = it },
              label = { Text("ISBN") }
          )

          Button(
              onClick = {
                  onAction(ScanBookIntent.LookUp(isbnText))
              }
          ) {
              Text("Look Up")
          }
      }

      Button(
          onClick = {
              onAction(ScanBookIntent.StartScan)
          },
          modifier = Modifier.padding(vertical = 30.dp).align(Alignment.CenterHorizontally)
      ) {
          Text("Scan")
      }

      HorizontalDivider(color = Color.Blue, thickness = 1.dp)

      when (state) {
          is ScanBookViewState.BookScanSuccess -> {
              BookFound(state.book)
          }

          is ScanBookViewState.BookScanError -> {
              BookNotFound()
          }

          else -> {}
      }
  }

}

@Composable
fun BookFound(book: BookResult)
{
    Column {
        Text(text= book.docs[0].title)
        Text(text= book.docs[0].authorName[0])
        ShelvesDropDown()
    }
}

@Composable
fun ShelvesDropDown()
{
    var expanded by remember { mutableStateOf(false) }
    var selectedShelf by remember { mutableStateOf(Shelf.MISC) }

    Row(
        modifier = Modifier
            .padding(16.dp).background(Color.Blue).width(50.dp)
    ) {
        Text(text = "Shelve On: ${selectedShelf.name}")
        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Shelf")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Shelf.entries.forEach { shelf ->
                DropdownMenuItem(
                    onClick = {
                        selectedShelf = shelf
                        expanded = false
                    },
                    text = {
                        Text(text = shelf.name)
                    }
                )
            }
        }
    }
}

@Composable
fun BookNotFound()
{
    Text("Could not look up book")
}