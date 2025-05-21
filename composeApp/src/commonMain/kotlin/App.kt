import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import core.theme.BookpediaTheme
import features.booklist.presentation.BookListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
@Preview
fun App() {
    BookpediaTheme {
        KoinContext {
            val navController = rememberNavController()
            
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable(
                    route = "home",
                    enterTransition = { 
                        slideIntoContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Right,
                            animationSpec = tween(300)
                        ) + fadeIn(animationSpec = tween(300))
                    },
                    exitTransition = {
                        slideOutOfContainer(
                            towards = AnimatedContentTransitionScope.SlideDirection.Left,
                            animationSpec = tween(300)
                        ) + fadeOut(animationSpec = tween(300))
                    }
                ) {
                    BookListScreen(viewModel = koinViewModel())
                }
            }
        }
    }
}