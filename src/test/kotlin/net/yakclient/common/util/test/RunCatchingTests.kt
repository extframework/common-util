package net.yakclient.common.util.test

import net.yakclient.common.util.runCatching
import java.lang.Exception
import kotlin.test.Test

class RunCatchingTests {
    @Test
    fun `Test Run Catching with null return`() {
        assert(runCatching(Exception::class) {
            null
        } == null)
    }

    @Test
    fun `Test exception throwing`() {
        assert(runCatching(Exception::class) {
            throw Exception()
        } == null)
    }
}