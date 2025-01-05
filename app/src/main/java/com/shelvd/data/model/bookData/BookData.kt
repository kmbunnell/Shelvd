package com.shelvd.data.model.bookData

import io.ktor.resources.*
import kotlinx.serialization.Contextual

@Resource("/isbn")
data class BookData(
    val identifiers: Identifiers,
    val title: String,
    val authors: List<Author>,
    val publishDate: String,
    val publishers: List<String>,
    val covers: List<Long>,
    val contributions: List<String>,
    val languages: List<Language>,
    val sourceRecords: List<String>,
    val localId: List<String>,
    val type: Type,
    val firstSentence: FirstSentence,
    val key: String,
    val numberOfPages: Long,
    val works: List<Work>,
    val classifications: Map<String, @Contextual Any>,
    val ocaid: String,
    val isbn10: List<String>,
    val isbn13: List<String>,
    val latestRevision: Long,
    val revision: Long,
    val created: Created,
    val lastModified: LastModified,
)

@Resource("identifiers")
data class Identifiers(
    val goodreads: List<String>,
    val librarything: List<String>,
)

@Resource("author")
data class Author(
    val key: String,
)

@Resource("language")
data class Language(
    val key: String,
)

@Resource("type")
data class Type(
    val key: String,
)

@Resource("firstSentence")
data class FirstSentence(
    val type: String,
    val value: String,
)

@Resource("work")
data class Work(
    val key: String,
)

@Resource("created")
data class Created(
    val type: String,
    val value: String,
)

@Resource("lastModified")
data class LastModified(
    val type: String,
    val value: String,
)

