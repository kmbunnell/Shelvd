package com.shelvd.ui.screens.common

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.shelvd.data.model.Edition

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EditionCheckList( editedFlags:MutableList<Edition>, onCheckedChanged:(List<Edition>)->Unit)
{
    LazyColumn {
        stickyHeader {
            Text(text = "Edition Info")
        }

        items( Edition.entries.size ){ idx->
            EditionItemRow(Edition.entries[idx], editedFlags, onCheckedChanged)
        }
    }
}

@Composable
fun EditionItemRow(edition: Edition, editedFlags: MutableList<Edition>, onCheckedChanged:(List<Edition>)->Unit ) {

    var isChecked by remember { mutableStateOf(editedFlags.contains(edition)) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                isChecked=it
                if(editedFlags.contains(edition))
                    editedFlags.remove(edition)
                else
                    editedFlags.add(edition)

                onCheckedChanged(editedFlags)
            }
        )
        Text( text = edition.screenName )

    }
}

