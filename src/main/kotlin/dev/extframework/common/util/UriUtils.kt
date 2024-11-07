package dev.extframework.common.util

import java.io.InputStream
import java.net.URI

public fun URI.openStream(): InputStream = toURL().openStream()

public fun URI.open(): InputStream = toURL().openStream()

public fun URI.readBytes(): ByteArray = open().readInputStream()

public fun URI.readAsSha1(): ByteArray = Hex.parseHex(String(readBytes()).trim().subSequence(0, 40))