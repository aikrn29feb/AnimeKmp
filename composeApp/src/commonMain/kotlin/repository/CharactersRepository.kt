package repository

import model.CharactersResponse
import network.DragonBallApi

class CharactersRepository {
    private val api = DragonBallApi()

    suspend fun getCharacters(page: Int = 1, limit: Int = 10): CharactersResponse {
        return api.getCharacters(page, limit)
    }
} 