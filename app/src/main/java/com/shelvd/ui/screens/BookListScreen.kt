package com.shelvd.ui.screens


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shelvd.data.model.Book
import com.shelvd.ui.BookListViewState


@Composable
fun BookListScreen(modifier: Modifier= Modifier,
                 state: BookListViewState
)
{
    when(state)
    {
        is BookListViewState.BooksLoaded-> BookList(state.books)
        is BookListViewState.Error -> Text(text= state.message)
        BookListViewState.Loading -> {Text(text= "Loading")}
    }
}

@Composable
fun BookList(books:List<Book>)
{

    LazyColumn {
        items(books.size){ idx->
            Text(text = books[idx].title)
        }
    }
}