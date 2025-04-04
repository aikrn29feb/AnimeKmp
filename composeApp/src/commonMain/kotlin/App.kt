import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import ui.CharactersScreen
import viewmodel.CharactersViewModel

@Composable
fun App() {
    val viewModel = CharactersViewModel()
    
    MaterialTheme {
        CharactersScreen(viewModel)
    }
} 