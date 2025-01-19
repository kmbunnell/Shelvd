package com.shelvd.ui.screens.scanBook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelvd.data.model.Book

@Composable
fun ScannBookRoute( viewModel: ScanBookVm = hiltViewModel() )
{
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ScanBookScreen(currentState, viewModel::handleIntent)

}
@Composable
fun ScanBookScreen( state: ScanBookViewState, onAction: (ScanBookIntent) -> Unit ){

  Column {
      Button(
          onClick = {
              onAction(ScanBookIntent.StartScan)
          },
          modifier = Modifier.padding(vertical = 60.dp)
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
fun BookFound(book: Book)
{
    Column {
        Text(text= book.docs[0].title)
        Text(text= book.docs[0].authorName[0])
    }
}

@Composable
fun BookNotFound()
{
    Text("Could not look up book")
}