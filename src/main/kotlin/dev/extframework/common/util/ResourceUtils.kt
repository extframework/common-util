package dev.extframework.common.util

import com.durganmcbroom.resources.*
import com.durganmcbroom.resources.toResource
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.url
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.FileOutputStream
import java.net.URI
import java.nio.channels.Channels
import java.nio.file.Path
import kotlin.io.path.toPath

public fun String.parseHex(): ByteArray = Hex.parseHex(this)

public fun URI.toResource(checkSum: ByteArray, checkType: ResourceAlgorithm = ResourceAlgorithm.SHA1): Resource =
    VerifiedResource(RemoteResource(HttpRequestBuilder().apply {
        url(toURL())
    }), checkType, checkSum)

@Deprecated("Use toPath().toResource() instead.", ReplaceWith("toPath().toResource()", "kotlin.io.path.toPath"))
public fun URI.toResource(): Resource = toPath().toResource()

public suspend infix fun Resource.copyTo(
    to: Path
): Path = withContext(Dispatchers.IO) {
    to.make()
    FileOutputStream(to.toFile()).use { out ->
        open().collect { value ->
            out.write(value)
        }
    }

    to
}
