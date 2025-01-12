package com.shelvd.data.model.authorData

@kotlinx.serialization.Serializable
data class AuthorData(val photos: List<Long>,
                      val name: String,
                      val sourceRecords: List<String>,
                      val alternateNames: List<String>,
                      val remoteIds: RemoteIds,
                      val key: String,
                      val birthDate: String,
                      val personalName: String,
                      val bio: String,
                      val type: Type,
                      val latestRevision: Long,
                      val revision: Long,
                      val created: Created,
                      val lastModified: LastModified,
)

@kotlinx.serialization.Serializable
data class RemoteIds(
    val viaf: String,
    val librarything: String,
    val storygraph: String,
    val goodreads: String,
    val wikidata: String,
    val amazon: String,
    val isni: String,
)

@kotlinx.serialization.Serializable
data class Type(
    val key: String,
)

@kotlinx.serialization.Serializable
data class Created(
    val type: String,
    val value: String,
)

@kotlinx.serialization.Serializable
data class LastModified(
    val type: String,
    val value: String,
)
