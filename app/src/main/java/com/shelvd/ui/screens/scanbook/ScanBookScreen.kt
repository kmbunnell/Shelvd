package com.shelvd.ui.screens.scanBook

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ladykbear.shelvd.R
import com.shelvd.data.model.ShelvedBook


@Composable
fun ScanBookRoute(viewModel: ScanBookVm = hiltViewModel()) {
    val currentState by viewModel.state.collectAsStateWithLifecycle()
    ScanBookScreen(currentState, viewModel::handleIntent)

}

@Composable
fun ScanBookScreen(state: ScanBookViewState, onAction: (ScanBookIntent) -> Unit) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            var selectedIndex by remember { mutableIntStateOf(0) }
            LookUpOptions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                selectedIndex
            ) {
                selectedIndex = it
                onAction(ScanBookIntent.ResetScreen)
            }

            LookUpBy(
                modifier = Modifier.padding(bottom = 10.dp),
                selectedIndex = selectedIndex,
                onAction = onAction
            )

            HorizontalDivider(color = Color.Blue, thickness = 1.dp)
        }

            when (state) {
                is ScanBookViewState.BookScanSuccess -> {
                    BookFoundScreen(state.book, state.isDup, onAction = onAction)
                }

                is ScanBookViewState.BookScanError -> {
                    BookNotFound()
                }

                is ScanBookViewState.Scanning -> {
                       CircularProgressIndicator(
                           modifier = Modifier.width(64.dp),
                           color = MaterialTheme.colorScheme.secondary,
                           trackColor = MaterialTheme.colorScheme.surfaceVariant
                       )
                }

                else -> {}
            }
        }

}

@Composable
fun BookInfo(book:ShelvedBook)
{
   Column (modifier = Modifier.padding(vertical = 5.dp)) {
       Text(text = book.title)
       Text(text = book.authors[0])
   }
}


@Composable
fun LookUpBy(modifier: Modifier, selectedIndex: Int, onAction: (ScanBookIntent) -> Unit) {
    if (selectedIndex == 0)
        ScanButton(modifier = modifier, onAction = onAction)
    else
        IsbnLookUpRow(modifier = modifier, onAction = onAction)

}

@Composable
fun LookUpOptions(modifier: Modifier = Modifier, selectedIndex: Int, onChanged: (Int) -> Unit) {

    val options = listOf(stringResource(R.string.scan), stringResource(R.string.isbn))

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onChanged(index) },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

@Composable
fun IsbnLookUpRow(modifier: Modifier = Modifier, onAction: (ScanBookIntent) -> Unit) {
    var isbnText by remember { mutableStateOf("9781635577020") }

    Row(modifier = modifier) {
        TextField(
            modifier = Modifier.padding(horizontal = 5.dp),
            value = isbnText,
            onValueChange = { isbnText = it },
            label = { Text(text = stringResource(R.string.isbn)) }
        )

        Button(
            onClick = {
                onAction(ScanBookIntent.LookUp(isbnText))
            }
        ) {
            Text(text = stringResource(R.string.find))
        }
    }
}

@Composable
fun BookNotFound() {
    Text("Could not look up book")
}