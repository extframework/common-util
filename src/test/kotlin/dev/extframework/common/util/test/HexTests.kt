package dev.extframework.common.util.test

import dev.extframework.common.util.Hex
import kotlin.test.Test

class HexTests  {
    @Test
    fun `Test Hex formatter`() {
        val hex = "3b4a11"
        val expectedArray = byteArrayOf(59, 74, 17)
        check(Hex.parseHex(hex).contentEquals(expectedArray))

        val bytes = byteArrayOf(10, 60, 21)
        val expectedHex = "0a3c15"
        check(Hex.formatHex(bytes) == expectedHex)
    }
}