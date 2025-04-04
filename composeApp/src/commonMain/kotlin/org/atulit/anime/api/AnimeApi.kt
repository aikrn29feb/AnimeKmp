package org.atulit.anime.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.atulit.anime.model.JikanResponse

class AnimeApi {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }
    
    suspend fun getCharacters(page: Int = 1): JikanResponse {
        println("Fetching characters page $page")
        val response = client.get("https://api.jikan.moe/v4/characters?page=$page&limit=10&order_by=favorites&sort=desc").body<JikanResponse>()
        println("Received ${response.data.size} characters")
        return response
    }
    
    companion object {
        val instance = AnimeApi()
    }
} 