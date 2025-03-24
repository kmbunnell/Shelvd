package com.shelvd.data.model

enum class Edition(val screenName:String, val bit:Int) {
    HARDBACK ("Hardback", 1 shl 0), // 1: 000001
    PAPERBACK ("Paperback", 1 shl 1), //2: 000010
    FIRST ("First Edition", 1 shl 2), //4: 000100
    ARC ("Arc", 1 shl 3), //6: 001000
    SPECIAL("Special/Collectors", 1 shl 4), //8: 010000
    SIGNED ("Signed", 1 shl 5), //10: 100000
}
