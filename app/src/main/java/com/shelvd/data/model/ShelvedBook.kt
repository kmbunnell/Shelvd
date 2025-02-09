package com.shelvd.data.model

data class ShelvedBook (val authors: List<String>,
                        val title:String,
                        val isbn:String="",
                        val shelf: Shelf = Shelf.WANT)



