package net.yakclient.common.util.resource

import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.InputStream
import java.net.URI
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.toPath

public class LocalResource(
    private val path: Path
) : SafeResource {
    override val uri: URI = path.toUri()

    public constructor(uri: URI) : this(Paths.get(uri))

    override fun open(): InputStream = BufferedInputStream(FileInputStream(path.toFile()))
}