import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin

fun main() {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "MovieMultiPlatform",
            ) {
            App()
        }

    }
}