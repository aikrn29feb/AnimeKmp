import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.seiko.imageloader.ImageLoader
import com.seiko.imageloader.component.setupDefaultComponents
import org.atulit.anime.App

fun main() {
    // Initialize image loader with minimal configuration
    ImageLoader {
        components {
            setupDefaultComponents()
        }
    }
    
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "Anime Characters"
        ) {
            App()
        }
    }
} 