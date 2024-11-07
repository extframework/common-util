package dev.extframework.common.util

import java.io.IOException
import java.io.InputStream
import java.net.*
import java.nio.file.Files
import kotlin.io.path.Path

public fun URL.isReachable(): Boolean = try {
    when (protocol) {
        "file" -> Files.exists(Path(file))
        "http", "https" -> (openConnection() as HttpURLConnection).responseCode == 200
        else -> {
            this.openConnection() is DeferredURLConnection
        }
    }
} catch (_: IOException) {
    false
}

public fun URL.uriAt(path: String): URI = URI(("${toExternalForm()}/$path"))

public fun URL.urlAt(vararg paths: String): URL = URL(paths.fold(toExternalForm()) { acc, it -> "$acc/$it" })

public fun (() -> InputStream).toURL(protocol: String? = null, host: String? = null, port : Int = -1, file: String? = null) : URL = URL(protocol, host, port, file, DeferredURLStreamHandler { this() })

public fun URL.withContent(stream: () -> InputStream): URL = URL(null, this.toExternalForm(), DeferredURLStreamHandler { stream() })

private class DeferredURLStreamHandler(
    private val delegate: (URL) -> InputStream
) : URLStreamHandler() {
    override fun openConnection(u: URL): URLConnection = DeferredURLConnection(u) { delegate(u) }
}

private class DeferredURLConnection(url: URL, private val provider: () -> InputStream) : URLConnection(url) {
    override fun connect() {
        // Nothing to do
    }

    override fun getInputStream(): InputStream = provider()
}