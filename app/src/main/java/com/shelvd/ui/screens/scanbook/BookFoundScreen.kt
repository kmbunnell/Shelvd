package com.shelvd.ui.screens.scanBook

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
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
import com.shelvd.data.model.ShelvedBook
import com.shelvd.ui.screens.common.ShelfDropDownRow

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
            DuplicateMessageColumn( book,
                onAction ={ onAction(ScanBookIntent.ResetScreen)} )
        } else {

            ShelfDropDownRow(
                selectedShelf,
                onShelfSelection = { selectedShelf = it },
                onShelveButtonClick = { onAction(ScanBookIntent.ShelveBook(book, selectedShelf)) })

        }
    }
}

@Composable
fun DuplicateMessageColumn(book:ShelvedBook, onAction: () -> Unit)
{
    Column() {
        Text(
            modifier = Modifier.padding(end = 10.dp),
            text = stringResource(R.string.dupDetected, book.title, book.shelf),
            color = Color.Red
        )
        Button(modifier = Modifier.align(Alignment.End),
            onClick = onAction
        ) {
            Text(text = stringResource(R.string.ok))
        }
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
