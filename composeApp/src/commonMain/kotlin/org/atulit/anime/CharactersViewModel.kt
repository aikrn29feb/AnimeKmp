package org.atulit.anime

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.atulit.anime.api.AnimeApi
import org.atulit.anime.model.CharacterData

sealed class CharactersUiState {
    data object Loading : CharactersUiState()
    data class Success(
        val characters: List<CharacterData>,
        val isLoadingMore: Boolean = false,
        val canLoadMore: Boolean = false
    ) : CharactersUiState()
    data class Error(val message: String) : CharactersUiState()
}

class CharactersViewModel {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val api = AnimeApi.instance
    
    var uiState by mutableStateOf<CharactersUiState>(CharactersUiState.Loading)
        private set
    
    private var currentPage = 1
    private var hasNextPage = false
    
    init {
        println("CharactersViewModel initialized")
        loadCharacters(refresh = true)
    }

    fun loadCharacters(refresh: Boolean = false) {
        println("Loading characters, refresh=$refresh")
        viewModelScope.launch {
            try {
                if (refresh) {
                    uiState = CharactersUiState.Loading
                    currentPage = 1
                } else {
                    // Update state to show loading more indicator
                    val currentCharacters = (uiState as? CharactersUiState.Success)?.characters ?: emptyList()
                    uiState = CharactersUiState.Success(
                        characters = currentCharacters, 
                        isLoadingMore = true,
                        canLoadMore = hasNextPage
                    )
                }
                
                println("Making API call")
                val response = api.getCharacters(currentPage)
                println("API call successful")
                
                // Update pagination info
                hasNextPage = response.pagination.has_next_page
                
                val newCharacters = response.data
                println("Got ${newCharacters.size} characters")
                
                val allCharacters = if (refresh) {
                    newCharacters
                } else {
                    val currentList = (uiState as? CharactersUiState.Success)?.characters ?: emptyList()
                    currentList + newCharacters
                }
                
                uiState = CharactersUiState.Success(
                    characters = allCharacters,
                    isLoadingMore = false,
                    canLoadMore = hasNextPage
                )
                println("Updated UI state with ${allCharacters.size} characters")
                
                // Increment page for next load
                if (hasNextPage) {
                    currentPage++
                }
            } catch (e: Exception) {
                println("Error loading characters: ${e.message}")
                e.printStackTrace()
                uiState = CharactersUiState.Error("Failed to load characters: ${e.message}")
            }
        }
    }

    fun loadMoreIfNeeded(index: Int) {
        val success = uiState as? CharactersUiState.Success ?: return
        
        // If we're viewing the last 3 items and not already loading more, and more items exist
        if (index >= success.characters.size - 3 && !success.isLoadingMore && success.canLoadMore) {
            loadCharacters(refresh = false)
        }
    }

    fun retryLoading() {
        loadCharacters(refresh = true)
    }
} 