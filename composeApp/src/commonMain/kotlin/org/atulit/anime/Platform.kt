package org.atulit.anime

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform