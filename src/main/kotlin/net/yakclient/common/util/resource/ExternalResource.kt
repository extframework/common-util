package net.yakclient.common.util.resource

import net.yakclient.common.util.openStream
import net.yakclient.common.util.readInputStream
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.net.URI
import java.security.DigestInputStream
import java.security.MessageDigest
import java.util.logging.Level
import java.util.logging.Logger

private const val NUM_ATTEMPTS = 3

public class ExternalResource(
    override val uri: URI,
    private val check: ByteArray,
    private val checkType: String = "SHA1"
) : SafeResource {
    private val logger = Logger.getLogger(ExternalResource::class.simpleName)

    private fun openInternal(): InputStream {
        assert(NUM_ATTEMPTS > 0)

        fun <T> doUntil(attempts: Int, supplier: (Int) -> T?): T? {
            for (i in 0 until attempts) {
                supplier(i)?.let { return@doUntil it }
            }
            return null
        }

        val digest = MessageDigest.getInstance(checkType)

        return ByteArrayInputStream(doUntil(NUM_ATTEMPTS) { attempt ->
                logger.log(Level.FINE, "Loading resource: $uri into memory for checksum processing")

                digest.reset()

                val b = DigestInputStream(uri.openStream(), digest).use(InputStream::readInputStream)

                if (digest.digest().contentEquals(check)) b
                else {
                    val attemptsLeft = NUM_ATTEMPTS - (attempt + 1)
                    logger.log(
                        Level.WARNING,
                        "Checksums failed for resource: '$uri'. Attempting $attemptsLeft more time${if (attemptsLeft == 1) "" else "s"}."
                    )
                    null
                }
            } ?: throw DownloadFailedException(uri))
        }

    override fun open(): InputStream = openInternal()
}

