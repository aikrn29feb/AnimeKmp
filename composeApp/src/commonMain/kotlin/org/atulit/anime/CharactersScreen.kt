package org.atulit.anime

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import org.atulit.anime.model.CharacterData

@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    // Debug UI state
    val state = viewModel.uiState
    println("Current UI state: $state")
    
    // Display UI based on state
    when (state) {
        is CharactersUiState.Loading -> {
            println("Displaying loading state")
            LoadingState()
        }
        is CharactersUiState.Success -> {
            println("Displaying success state with ${state.characters.size} characters")
            CharactersList(
                characters = state.characters,
                isLoadingMore = state.isLoadingMore,
                onLoadMore = viewModel::loadMoreIfNeeded
            )
        }
        is CharactersUiState.Error -> {
            println("Displaying error state: ${state.message}")
            ErrorState(
                message = state.message,
                onRetry = viewModel::retryLoading
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry
        ) {
            Text("Retry")
        }
    }
}

@Composable
private fun CharactersList(
    characters: List<CharacterData>,
    isLoadingMore: Boolean,
    onLoadMore: (Int) -> Unit
) {
    if (characters.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No characters found")
        }
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = characters,
            key = { _, character -> character.mal_id }
        ) { index, character ->
            println("Rendering character at index $index: ${character.name}")
            CharacterCard(character)
            onLoadMore(index)
        }
        
        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
private fun CharacterCard(character: CharacterData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Character image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0E0E0))
            ) {
                // Prioritize WebP image if available, fallback to JPG
                val imageUrl = character.images?.webp?.image_url 
                    ?: character.images?.jpg?.image_url
                    ?: ""
                
                if (imageUrl.isNotEmpty()) {
                    // For debugging
                    println("Loading image: $imageUrl")
                    
                    // Load image using rememberImagePainter
                    val painter = rememberImagePainter(imageUrl)
                    androidx.compose.foundation.Image(
                        painter = painter,
                        contentDescription = character.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    // Fallback for missing image
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF3F51B5)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = character.name.firstOrNull()?.toString() ?: "?",
                            style = MaterialTheme.typography.h5,
                            color = Color.White
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.h6
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Favorites: ${character.favorites}",
                    style = MaterialTheme.typography.body2
                )
                
                if (character.about.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = character.about,
                        style = MaterialTheme.typography.body2,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
} 