package com.shelvd.data.model.bookData

import io.ktor.resources.*

@Resource("/isbn")
data class BookData(
    val title: String,
    val authors: List<Author>,
    val covers: List<Long>,
)

@Resource("author")
data class Author(
    val key: String,
)


