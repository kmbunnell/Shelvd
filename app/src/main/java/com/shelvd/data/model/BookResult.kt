package com.shelvd.data.model


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
data class BookResult(
    val docs: List<Doc>
)

@Serializable
data class Doc(
    @JsonNames("author_name")
    val authorName: List<String>,
    @JsonNames("cover_edition_key")
    val coverEditionKey: String,
    val title: String,
)

