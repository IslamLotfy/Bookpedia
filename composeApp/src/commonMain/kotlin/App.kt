
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import booklist.presentation.TrendingBooksViewModel
import booklist.presentation.composables.TrendingThisMonthContent
import booklist.presentation.composables.TrendingThisWeekContent
import booklist.presentation.composables.TrendingThisYearContent
import booklist.presentation.composables.TrendingTodayContent
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import presentation.composables.TabView

@Composable
@Preview
fun App() {
    MaterialTheme {
        KoinContext {
            NavHost(
                navController = rememberNavController(),
                startDestination = "home"
            ) {
                composable(route = "home") {
                    val pages = listOf("Today","This week","This month","This year")
                    val pagerState = rememberPagerState(pageCount = { pages.size })
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(title = {
                            Text("Treding Books")
                        })
                        TabView(pagerState = pagerState,data = pages,coroutineScope = rememberCoroutineScope())
                        HorizontalPager(state = pagerState) { page ->
                            when (page) {
                                0 -> {
                                    TrendingTodayContent(viewModel = koinViewModel<TrendingBooksViewModel>())
                                }

                                1 -> {
                                    TrendingThisWeekContent(viewModel = koinViewModel<TrendingBooksViewModel>())
                                }

                                2 -> {
                                    TrendingThisMonthContent(viewModel = koinViewModel<TrendingBooksViewModel>())
                                }

                                3 -> {
                                    TrendingThisYearContent(viewModel = koinViewModel<TrendingBooksViewModel>())
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}