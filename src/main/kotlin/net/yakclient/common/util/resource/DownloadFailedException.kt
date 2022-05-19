package net.yakclient.common.util.resource

import java.net.URI

public class DownloadFailedException(resource: URI) : Exception("Download failed for resource: $resource")