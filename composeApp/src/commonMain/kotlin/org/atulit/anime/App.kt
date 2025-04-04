package org.atulit.anime

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel = remember { CharactersViewModel() }
    
    MaterialTheme {
        CharactersScreen(viewModel)
    }
}