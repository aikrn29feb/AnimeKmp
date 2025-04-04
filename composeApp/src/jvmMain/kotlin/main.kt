import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.atulit.anime.App
import org.atulit.anime.CoilInitializer

fun main() {
    // Initialize Coil for image loading
    CoilInitializer.init()
    
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Anime Characters"
        ) {
            App()
        }
    }
} 