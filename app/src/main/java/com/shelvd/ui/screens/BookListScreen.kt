package com.shelvd.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.shelvd.ui.BookIntent
import com.shelvd.ui.BookListViewState
import com.shelvd.ui.MainViewModel


@Composable
fun BookListScreen(modifier: Modifier= Modifier)
{
    val viewModel : MainViewModel = viewModel()
    val currentState by viewModel.state.collectAsStateWithLifecycle ()
    BookListContent(currentState, viewModel::handleIntent )

}

@Composable
fun BookListContent(state:BookListViewState, onAction: (BookIntent) -> Unit) {

    when( state )
    {
        is BookListViewState.BooksLoaded->{
            Log.d("TST", "BookListContent: BooksLoaded: ${state.books.size}")
            Column {
                LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
                    items(state.books.size) { idx ->
                        Text(text = state.books[idx].title)
                    }
                }
                Button(
                    modifier = Modifier.padding(top = 50.dp),
                    onClick = {onAction(BookIntent.AddBook)}) {
                    Text("Add")
                }
            }
        }
        is BookListViewState.Error -> Text(text= state.message)
        BookListViewState.Loading -> {Text(text= "Loading")}
    }


}