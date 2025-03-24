package com.shelvd.data

import com.shelvd.data.model.Edition

object Util {
    const val BASE_URL = "https://openlibrary.org"

    fun calculateEditionFlags(newFlags: List<Edition>): Int {
        var newFlagsValue = 0
        Edition.entries.forEach { flag ->
            if (newFlags.contains(flag))
                newFlagsValue = newFlagsValue or flag.bit
        }

        return newFlagsValue
    }
}