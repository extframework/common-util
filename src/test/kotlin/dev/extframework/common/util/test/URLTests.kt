package dev.extframework.common.util.test

import dev.extframework.common.util.readInputStream
import dev.extframework.common.util.withContent
import java.net.URL
import kotlin.test.Test

class URLTests {
    @Test
    fun `Test custom url connection handlers`() {
        val content = "Hello"
        val url = URL("https://google.com").withContent { content.byteInputStream() }
        println(String(url.openStream().readInputStream()))
    }
}