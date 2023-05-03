package net.yakclient.common.util.resource

import net.yakclient.common.util.equalsAny
import net.yakclient.common.util.openStream
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.toPath

public class LocalResource(
    override val uri: URI
) : SafeResource {
    init {
        check(uri.scheme.equalsAny("file", "jar")) { "File must be on local file system!" }
    }

    override fun open(): InputStream = uri.openStream()
}