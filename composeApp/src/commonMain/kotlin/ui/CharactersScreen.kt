package ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.CharactersViewModel

@Composable
fun CharactersScreen(viewModel: CharactersViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dragon Ball Characters") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (uiState.isLoading && uiState.characters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (uiState.errorMessage != null && uiState.characters.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Error: ${uiState.errorMessage}")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(uiState.characters) { character ->
                        CharacterItem(character = character)
                    }
                }

                // Pagination controls
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { viewModel.previousPage() },
                        enabled = uiState.currentPage > 1
                    ) {
                        Text("Previous")
                    }

                    Text(
                        text = "Page ${uiState.currentPage} of ${uiState.totalPages}",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )

                    Button(
                        onClick = { viewModel.nextPage() },
                        enabled = uiState.currentPage < uiState.totalPages
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
} 