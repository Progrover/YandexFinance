package dev.progrover.core.uicommon.utils

import coil.request.CachePolicy

object ImageRequestDefaults {
    val cachePolicy = CachePolicy.ENABLED
    const val maxAgeSeconds = 604800 // 1 week
    const val staleWhileRevalidateSeconds = 84600 // 1 day
}