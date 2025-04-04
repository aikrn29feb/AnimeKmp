package org.atulit.anime

import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache
import java.io.File

object CoilInitializer {
    fun init() {
        val imageLoader = ImageLoader.Builder()
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(File(System.getProperty("java.io.tmpdir"), "coil_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
        
        Coil.setImageLoader(imageLoader)
    }
} 