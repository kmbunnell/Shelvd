package com.shelvd.ui.screens.shelves

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shelvd.data.model.ShelvedBook
import com.shelvd.data.model.Shelf
import kotlin.math.exp


@Composable
fun ShelvesRoute(viewModel: ShelvesVM = hiltViewModel()) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ShelvesScreen(currentState, viewModel::handleIntent)

}

@Composable
fun ShelvesScreen(state: ShelvesViewState, onAction: (ShelvesIntent) -> Unit) {
    Column {
        ShelfRow(onAction)

        when (state) {
            is ShelvesViewState.ShelvedBooks -> {
                BookShelf(state.shelvedBooks)
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
fun BookShelf(shelvedBooks: List<ShelvedBook>) {
    var expandedIdx by remember {
        mutableIntStateOf(-1)
    }
    LazyColumn(modifier = Modifier
        .padding(top = 20.dp)
        .padding(horizontal = 20.dp)) {
        items(shelvedBooks.size) { idx ->
            val book = shelvedBooks[idx]
            BookShelfItem(
                expandedIdx, idx, book.title,
                onSelected = { expandedIdx = if(expandedIdx == idx) -1 else idx },
                onReshelve = {},
                onRemove = {})

        }
    }
}

@Composable
fun BookShelfItem(expanded:Int, idx:Int, title:String, onSelected: ()->Unit, onReshelve: ()->Unit, onRemove:()->Unit)
{
    Text(modifier = Modifier
        .padding(bottom = 3.dp)
        .selectable(
            selected = expanded == idx,
            onClick = { onSelected() }),
        text = title)

    if(expanded == idx)
        Text(text="hi")


}


@Composable
fun ShelfRow(onAction: (ShelvesIntent) -> Unit) {
    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        LazyRow {
            items(Shelf.entries.size) { idx ->
                Text(
                    text = Shelf.entries[idx].name,
                    modifier = Modifier
                        .padding(horizontal = 5.dp)
                        .selectable(
                            selected = selectedIndex == idx,
                            onClick = {
                                selectedIndex = idx
                                onAction(ShelvesIntent.LoadBooks(Shelf.entries[idx]))
                            })
                        .background(
                            if (selectedIndex == idx) Color.Gray
                            else Color.Transparent
                        )
                )
            }
        }
    }
}




