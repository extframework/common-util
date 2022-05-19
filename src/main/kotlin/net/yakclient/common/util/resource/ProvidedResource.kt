package net.yakclient.common.util.resource

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URI

public class ProvidedResource(override val uri: URI, private val provider: () -> ByteArray) : SafeResource {
    override fun open(): InputStream = ByteArrayInputStream(provider())
}