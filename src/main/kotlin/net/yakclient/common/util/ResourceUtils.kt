package net.yakclient.common.util

import com.durganmcbroom.resources.*
import java.io.FileOutputStream
import java.net.URI
import java.nio.channels.Channels
import java.nio.file.Path
import java.util.*
import kotlin.io.path.toPath

public fun String.parseHex(): ByteArray = HexFormat.of().parseHex(this)

public fun URI.toResource(checkSum: ByteArray, checkType: ResourceAlgorithm = ResourceAlgorithm.SHA1): Resource =
    VerifiedResource(toURL().toResource(), checkType, checkSum)

@Deprecated("Use toPath().toResource() instead.", ReplaceWith("toPath().toResource()", "kotlin.io.path.toPath"))
public fun URI.toResource(): Resource = toPath().toResource()

public infix fun Resource.copyTo(
    to: Path
): Path = Channels.newChannel(openStream()).use { cin ->
    to.make()
    FileOutputStream(to.toFile()).use { fout ->
        fout.channel.transferFrom(cin, 0, Long.MAX_VALUE)
    }
    to
}
