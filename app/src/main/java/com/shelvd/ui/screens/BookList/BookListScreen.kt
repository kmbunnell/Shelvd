package com.shelvd.ui.screens.BookList


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun BookListScreen(modifier: Modifier = Modifier) {
     Text(modifier = Modifier.padding(top = 50.dp), text = "Books")
    /*val viewModel: MainViewModel = viewModel()
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    BookListContent(currentState, viewModel::handleIntent)*/


}

@Composable
fun BookListContent(state: BookListViewState, onAction: (BookIntent) -> Unit) {
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }
    when (state) {
        is BookListViewState.BooksLoaded -> {
            Log.d("TST", "BookListContent: BooksLoaded: ${state.shelvedBooks.size}")
            Column {
                LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                    items(state.shelvedBooks.size) { idx ->
                        Text(
                            text = state.shelvedBooks[idx].title,
                            modifier = Modifier.selectable(
                                selected = selectedIndex == idx,
                                onClick = { selectedIndex = if (selectedIndex == idx) -1 else idx })
                                .background(
                                    if (selectedIndex == idx) Color.Gray
                                    else Color.Transparent
                                )
                        )
                    }
                }

                Button(
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = { onAction(BookIntent.AddBook) }) {
                    Text("Add")
                }

                Button(
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = { onAction(BookIntent.RemoveBook(selectedIndex)) }) {
                    Text("Remove")
                }
            }
        }

        is BookListViewState.Error -> Text(text = state.message)
        BookListViewState.Loading -> {
            Text(text = "Loading")
        }
    }
}