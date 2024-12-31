package com.shelvd.ui.screens.shelves

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelvd.data.model.Book
import com.shelvd.data.model.Shelf


@Composable
fun ShelvesRoute( viewModel: ShelvesVM = hiltViewModel())
{
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ShelvesScreen(currentState, viewModel::handleIntent)

}

@Composable
fun ShelvesScreen(state:ShelvesViewState,  onAction: (ShelvesIntent) -> Unit) {
    Column {
        ShelfRow(onAction)

        when (state) {
            is ShelvesViewState.ShelvedBooks -> {
                BookShelf(state.books)
            }

            is ShelvesViewState.Error -> TODO()
            ShelvesViewState.Loading -> Loading()
        }
    }
}

@Composable
fun Loading()
{
    Text("Loading")
}

@Composable
fun BookShelf(books:List<Book>)
{
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
        items(books.size) { idx ->
            Text(text = books[idx].title)
        }
    }
}
@Composable
fun ShelfRow( onAction: (ShelvesIntent) -> Unit )
{
    var selectedIndex by remember {
        mutableIntStateOf(-1)
    }

    Column(modifier=Modifier.padding(horizontal = 10.dp)) {
        LazyRow {
            items(Shelf.entries.size) { idx ->
                Text(
                    text =Shelf.entries[idx].name,
                    modifier = Modifier.padding(horizontal = 5.dp).selectable(
                        selected = selectedIndex == idx,
                        onClick = { selectedIndex = if (selectedIndex == idx) -1 else idx
                                    onAction(ShelvesIntent.LoadBooks(Shelf.entries[idx]))})
                        .background(
                            if (selectedIndex == idx) Color.Gray
                            else Color.Transparent
                        )
                )
            }
        }
    }
}




