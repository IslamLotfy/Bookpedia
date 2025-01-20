import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import booklist.presentation.TrendingBooksViewModel
import bookpedia.composeapp.generated.resources.Res
import bookpedia.composeapp.generated.resources.compose_multiplatform
import core.domain.DataState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            NavHost(
                navController = rememberNavController(),
                startDestination = "home"
            ){
                composable(route = "home"){
                    val viewModel = koinViewModel<TrendingBooksViewModel>()
                    var showContent by remember { mutableStateOf(false) }
                    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(onClick = { showContent = !showContent }) {
                            Text("Click me!")
                        }
                        AnimatedVisibility(showContent) {
                            val greeting = remember { Greeting().greet() }

                            val books = viewModel.getTrendingBooks().collectAsStateWithLifecycle(initialValue = DataState.Idle)

                            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                                Image(painterResource(Res.drawable.compose_multiplatform), null)
                                Text("Compose: ${books.value}")
                            }
                        }
                    }
                }
            }
        }
    }
}