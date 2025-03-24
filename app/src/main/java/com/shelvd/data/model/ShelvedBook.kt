package com.shelvd.data.model

data class ShelvedBook(
    val authors: List<String>,
    val title: String,
    val isbn: String = "",
    val shelf: Shelf = Shelf.WANT,
    val editionFlags: Int = 0,
    val notes:String = ""

) {

    fun editionFlagList(): List<Edition> {
        val flags = mutableListOf<Edition>()
        Edition.entries.forEach {
            if (hasEditionFlag(it))
                flags.add(it)
        }

        return flags.toList()
    }

    @Synchronized
    private fun hasEditionFlag(edition: Edition): Boolean = (editionFlags and edition.bit != 0)

   /* @Synchronized
    fun addEditionFlag(edition: Edition) = this.copy(editionFlags = editionFlags or edition.bit)

    @Synchronized
    private fun removeEditionFlag(edition: Edition) =
        this.copy(editionFlags = editionFlags and edition.bit.inv())

    @Synchronized
    fun updateEditionFlags(editionFlags: Int) =
        this.copy(editionFlags = editionFlags)
*/

}



