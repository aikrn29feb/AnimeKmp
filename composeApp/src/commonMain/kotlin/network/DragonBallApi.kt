package network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import model.CharactersResponse

class DragonBallApi {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private val baseUrl = "https://dragonball-api.com/api"

    suspend fun getCharacters(page: Int = 1, limit: Int = 10): CharactersResponse {
        return httpClient.get("$baseUrl/characters") {
            parameter("page", page)
            parameter("limit", limit)
        }.body()
    }
} 