package features.booklist.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import features.booklist.domain.model.Book

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TrendingToday(
    viewModel: BookListViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = selectedBook,
            transitionSpec = {
                (slideInHorizontally { fullWidth -> fullWidth } + fadeIn() with
                        slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut())
                    .using(SizeTransform(clip = false))
            }
        ) { targetBook ->
            if (targetBook != null) {
                BookDetails(
                    book = targetBook,
                    onBackPressed = { selectedBook = null },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Trending Books",
                                    style = MaterialTheme.typography.h5.copy(
                                        fontWeight = FontWeight.ExtraBold
                                    )
                                )
                            },
                            backgroundColor = MaterialTheme.colors.surface,
                            elevation = 4.dp
                        )
                    },
                    modifier = Modifier.windowInsetsPadding(
                        WindowInsets.statusBars.only(WindowInsetsSides.Top)
                    )
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            color = MaterialTheme.colors.background,
                            elevation = 1.dp
                        ) {
                            TrendingTabs(
                                selectedCategory = uiState.trendingBooks.selectedCategory,
                                selectedTimeRange = uiState.trendingBooks.selectedTimeRange,
                                onCategorySelected = viewModel::updateCategory,
                                onTimeRangeSelected = viewModel::updateTimeRange,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        BookGrid(
                            state = uiState.trendingBooks.books,
                            onBookSelected = { book -> selectedBook = book },
                            onRetry = viewModel::refresh,
                            onRefresh = viewModel::refresh,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
