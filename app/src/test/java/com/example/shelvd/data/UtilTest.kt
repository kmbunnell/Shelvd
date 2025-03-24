package com.example.shelvd.data

import com.shelvd.data.Util
import com.shelvd.data.model.Edition
import org.junit.Test
import org.junit.Assert.assertEquals

class UtilTest {

    @Test
    fun `calculateEditionFlags No Edition Flag`() {
        val flag = Util.calculateEditionFlags(listOf())
        assertEquals(flag, 0)
    }

    @Test
    fun `calculateEditionFlags HardBackEdition`() {
        val flag = Util.calculateEditionFlags(listOf(Edition.HARDBACK))
        assertEquals(flag, 1)
    }

    @Test
    fun `calculateEditionFlags Paperback Edition`() {
        val flag = Util.calculateEditionFlags(listOf(Edition.PAPERBACK))
        assertEquals(flag, 2)
    }

    @Test
    fun `calculateEditionFlags First Edition`() {
        val flag = Util.calculateEditionFlags(listOf(Edition.FIRST))
        assertEquals(flag, 4)
    }

    @Test
    fun `calculateEditionFlags Arc Edition`() {
        val flag = Util.calculateEditionFlags(listOf(Edition.ARC))
        assertEquals(flag, 8)
    }

    @Test
    fun `calculateEditionFlags Special Edition`() {
        val flag = Util.calculateEditionFlags(listOf(Edition.SPECIAL))
        assertEquals(flag, 16)
    }

    @Test
    fun `calculateEditionFlags Signed Edition`() {
        val flag = Util.calculateEditionFlags(listOf(Edition.SIGNED))
        assertEquals(flag, 32)
    }

    @Test
    fun `calculateEditionFlags ALL Edition`() {
        val flag = Util.calculateEditionFlags(
            listOf(
                Edition.HARDBACK,
                Edition.PAPERBACK,
                Edition.FIRST,
                Edition.ARC,
                Edition.SPECIAL,
                Edition.SIGNED
            )
        )
        assertEquals(flag, 63)
    }
}