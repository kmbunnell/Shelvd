package com.shelvd.ui.screens.shelves

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import com.shelvd.ui.screens.common.ShelfDropDownRow
import kotlin.math.exp


@Preview(showBackground = true)
@Composable
fun ShelvesLoadedScreenPreview()
{
    val shelvedBooks = listOf(
        ShelvedBook(authors = listOf("Kristina B"), title = "Best enemies to lovers", isbn = "1234",
        shelf = Shelf.OWNED),
        ShelvedBook(authors = listOf("Kristina B", "Bailey B"), title = "Super fun RomCom", isbn = "4567",
            shelf = Shelf.OWNED),
        ShelvedBook(authors = listOf("Kristina B"), title = "Next Book ", isbn = "7894",
            shelf = Shelf.PREORDERED))

    ShelvesScreen(state = ShelvesViewState.ShelvedBooks(currentShelf = Shelf.OWNED, shelvedBooks = shelvedBooks)){}
}

@Composable
fun ShelvesRoute(viewModel: ShelvesVM = hiltViewModel()) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.handleIntent(ShelvesIntent.LoadBooks(Shelf.OWNED))
    }

    ShelvesScreen(currentState, viewModel::handleIntent)
}

@Composable
fun ShelvesScreen(state: ShelvesViewState, onAction: (ShelvesIntent) -> Unit) {
    Column {

        HorizontalDivider(color = Color.Blue, thickness = 1.dp, modifier = Modifier.padding(bottom = 3.dp))
        when (state) {
            is ShelvesViewState.ShelvedBooks -> {
                ShelfRow(state.currentShelf, onAction)
                BookShelf(
                    state.shelvedBooks,
                    onDeleteBook = {
                        onAction(ShelvesIntent.DeleteBook(it))
                    },
                    onReshelveBook = { book, shelf ->
                        onAction(ShelvesIntent.ReshelveBook(book, shelf))
                    }
                )
            }

            is ShelvesViewState.Error -> TODO()
            ShelvesViewState.Loading -> Loading()
        }
    }
}

@Composable
fun Loading() {
    Text("Loading")
}

@Composable
fun BookShelf(
    shelvedBooks: List<ShelvedBook>,
    onDeleteBook: (ShelvedBook) -> Unit,
    onReshelveBook: (ShelvedBook, Shelf) -> Unit
) {
    var selectedIsbn by remember {
        mutableStateOf("")
    }

    LazyColumn(
        modifier = Modifier
            .padding(top = 20.dp)
            .padding(horizontal = 20.dp)
    ) {
        items(shelvedBooks.size) { idx ->
            val book = shelvedBooks[idx]

            BookShelfItem(
                book = book,
                showEditOptions = (selectedIsbn == book.isbn),
                onSelected = { selectedIsbn = if (selectedIsbn == book.isbn) "" else book.isbn },
                onReshelveBook = onReshelveBook,
                onRemove = onDeleteBook
            )
        }
    }
}

@Composable
fun BookShelfItem(
    book: ShelvedBook,
    showEditOptions: Boolean,
    onSelected: () -> Unit,
    onReshelveBook: (ShelvedBook, Shelf) -> Unit,
    onRemove: (ShelvedBook) -> Unit
) {
    var bookCurrentShelf by remember { mutableStateOf(book.shelf) }

    Text(
        modifier = Modifier
            .padding(bottom = 3.dp)
            .clickable { onSelected() }
            .background(
                if (showEditOptions) Color.Blue
                else Color.Transparent
            ) ,
        text = book.title
    )

    if (showEditOptions)

        Column(modifier = Modifier.padding(start = 10.dp), horizontalAlignment = Alignment.Start) {
            HorizontalDivider(color = Color.Blue, thickness = 1.dp)
            ShelfDropDownRow(
                selectedShelf = bookCurrentShelf,
                onShelfSelection = { bookCurrentShelf = it },
                onShelveButtonClick = { onReshelveBook(book, bookCurrentShelf) }
            )
            Button( onClick = { onRemove(book) }) {
                Text(
                    "Delete"
                )
            }
            HorizontalDivider(color = Color.Blue, thickness = 1.dp, modifier = Modifier.padding(bottom = 3.dp))
        }
}


@Composable
fun ShelfRow(currentShelf: Shelf, onAction: (ShelvesIntent) -> Unit) {
    var selectedShelf by remember {
        mutableStateOf(currentShelf)
    }

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        LazyRow {
            items(Shelf.entries.size) {  idx->
                Text(
                    text = Shelf.entries[idx].name,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .selectable(
                            selected = selectedShelf == Shelf.entries[idx],
                            onClick = {
                                selectedShelf = Shelf.entries[idx]
                                onAction(ShelvesIntent.LoadBooks(Shelf.entries[idx]))
                            })
                        .background(
                            if (selectedShelf == Shelf.entries[idx]) Color.Gray
                            else Color.Transparent
                        )
                )
            }
        }
    }
}




