package features.booklist.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.domain.DataState
import features.booklist.presentation.composables.BookDetails

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TrendingToday(
    viewModel: BookListViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedContent(
            targetState = state.selectedBook,
            transitionSpec = {
                (slideInHorizontally { fullWidth -> fullWidth } + fadeIn() with
                        slideOutHorizontally { fullWidth -> -fullWidth } + fadeOut())
                    .using(SizeTransform(clip = false))
            }
        ) { targetBook ->
            if (targetBook != null) {
                BookDetails(
                    book = targetBook,
                    onBackPressed = viewModel::onClearSelectedBook,
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
                                selectedCategory = state.selectedCategory,
                                onCategorySelected = viewModel::onCategorySelected,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        BookGrid(
                            books = state.books,
                            isLoading = state.isLoading,
                            isLoadingMore = state.isLoadingMore,
                            error = state.error,
                            onBookSelected = viewModel::onBookSelected,
                            onRefresh = { viewModel.fetchTrendingBooks(reset = true) },
                            onLoadMore = viewModel::loadMoreTrendingBooks,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}
