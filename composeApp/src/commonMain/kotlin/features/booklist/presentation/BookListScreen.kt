package features.booklist.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.domain.AppError
import features.booklist.domain.model.Book
import features.booklist.presentation.composables.BookmarkIcon
import features.booklist.presentation.composables.SearchBar
import features.booklist.presentation.composables.SearchResults


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BookListScreen(
    viewModel: BookListViewModel,
    showFavoritesOnly: Boolean = false,
    onNavigateToDetails: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val favoriteStatusMap by viewModel.favoriteStatusMap.collectAsStateWithLifecycle()
    val favoriteBooks by viewModel.favoriteBooks.collectAsStateWithLifecycle()

    // Set favorites mode based on parameter
    LaunchedEffect(showFavoritesOnly) {
        if (showFavoritesOnly) {
            viewModel.showFavoriteBooks()
        } else if (state.showingFavorites) {
            viewModel.hideFavoriteBooks()
        }
    }

    // Navigate to details if a book is selected
    LaunchedEffect(state.selectedBook?.id) {
        val selectedBook = state.selectedBook
        if (selectedBook != null) {
            onNavigateToDetails(selectedBook.id)
            // Clear the selected book after navigation
            viewModel.onClearSelectedBook()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.showingFavorites) "Favorite Books" else "Bookpedia",
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 4.dp,
                actions = {
                    
                }
            )
        },
        modifier = Modifier.windowInsetsPadding(
            WindowInsets.statusBars.only(WindowInsetsSides.Top)
        )
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Only show search if not in favorites view
            if (!state.showingFavorites) {
                SearchBar(
                    query = state.searchQuery,
                    onQueryChange = { query ->
                        viewModel.updateSearchQuery(query)
                        if (query.isEmpty()) {
                            viewModel.clearSearch()
                        }
                    },
                    onSearch = viewModel::searchBooks,
                    onClearSearch = viewModel::clearSearch,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // If in favorites view, show favorites
            if (state.showingFavorites) {
                FavoritesGrid(
                    books = favoriteBooks,
                    onBookSelected = viewModel::onBookSelected,
                    onToggleFavorite = viewModel::toggleFavorite,
                    favoriteStatusMap = favoriteStatusMap,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Regular search and browse view
                AnimatedVisibility(
                    visible = state.isSearching,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    SearchResults(
                        books = state.searchResults,
                        isLoading = state.isSearchLoading || state.isSearchLoadingMore,
                        error = state.searchError,
                        onBookClicked = viewModel::onBookSelected,
                        onFavoriteToggle = viewModel::toggleFavorite,
                        favoriteStatusMap = favoriteStatusMap,
                        onLoadMore = viewModel::loadMoreSearchResults,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                AnimatedVisibility(
                    visible = !state.isSearching,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        TrendingTabs(
                            selectedCategory = state.selectedCategory,
                            onCategorySelected = viewModel::onCategorySelected,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Box(modifier = Modifier.weight(1f)) {
                            BookGrid(
                                books = state.books,
                                isLoading = state.isLoading,
                                isLoadingMore = state.isLoadingMore,
                                error = state.error,
                                onBookSelected = viewModel::onBookSelected,
                                onFavoriteToggle = viewModel::toggleFavorite,
                                favoriteStatusMap = favoriteStatusMap,
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
}



@Composable
fun FavoritesGrid(
    books: List<Book>,
    onBookSelected: (Book) -> Unit,
    onToggleFavorite: (Book) -> Unit,
    favoriteStatusMap: Map<String, Boolean>,
    modifier: Modifier = Modifier
) {
    if (books.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(48.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "No favorite books yet",
                    style = MaterialTheme.typography.h6,
                    color = Color.Gray
                )
            }
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            itemsIndexed(
                items = books,
                // Create a composite key that includes both book ID and index position
                key = { index, book -> "${book.id}_favorite_$index" }
            ) { _, book ->
                BookGridItemWithFavorite(
                    book = book,
                    isFavorite = favoriteStatusMap[book.id] ?: false,
                    onClick = { onBookSelected(book) },
                    onFavoriteClick = { onToggleFavorite(book) }
                )
            }
        }
    }
}

@Composable
fun BookGrid(
    books: List<Book>,
    isLoading: Boolean,
    isLoadingMore: Boolean,
    error: AppError?,
    onBookSelected: (Book) -> Unit,
    onFavoriteToggle: (Book) -> Unit,
    favoriteStatusMap: Map<String, Boolean>,
    onRefresh: () -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyGridState()

    // Detect when we need to load more items
    val loadMore = remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1

            // Load more when we reach the last 4 items
            lastVisibleItemIndex > (totalItems - 4)
        }
    }

    LaunchedEffect(loadMore.value) {
        if (loadMore.value && !isLoading && !isLoadingMore && books.isNotEmpty()) {
            onLoadMore()
        }
    }

    PullRefreshBox(
        isRefreshing = isLoading && books.isEmpty(),
        onRefresh = onRefresh,
        modifier = modifier
    ) {
        when {
            isLoading && books.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colors.primary)
                }
            }
            error != null && books.isEmpty() -> {
                ErrorContent(
                    error = error,
                    onRetry = onRefresh,
                    modifier = Modifier.fillMaxSize()
                )
            }
            books.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No books found",
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                }
            }
            else -> {
               LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = listState,
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(
                        items = books,
                        // Create a composite key that includes both book ID and index position
                        key = { index, book -> "${book.id}_grid_$index" }
                    ) { _, book ->
                        BookGridItemWithFavorite(
                            book = book,
                            isFavorite = favoriteStatusMap[book.id] ?: false,
                            onClick = { onBookSelected(book) },
                            onFavoriteClick = { onFavoriteToggle(book) }
                        )
                    }

                    if (isLoadingMore) {
                        item(span = { GridItemSpan(2) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(32.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorContent(
    error: AppError,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Error",
            tint = MaterialTheme.colors.error,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = when (error) {
                is AppError.Exception -> "Error: ${error.errorMessage}"
                is AppError.Business -> error.errorMessage
                is AppError.Server -> "Server error, please try again later."
                is AppError.Frontend -> "Application error, please try again."
            },
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.error
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PullRefreshBox(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val pullRefreshState = androidx.compose.material.pullrefresh.rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = onRefresh
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        content()

        // Only show pull-to-refresh indicator when actually refreshing through pull gesture
        // This prevents the indicator from showing with the central loading spinner
        val isPulling = pullRefreshState.progress > 0f
        if (isRefreshing && isPulling || pullRefreshState.progress > 0f) {
            androidx.compose.material.pullrefresh.PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                backgroundColor = MaterialTheme.colors.surface,
                contentColor = MaterialTheme.colors.primary,
                scale = true
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookGridItemWithFavorite(
    book: Book,
    isFavorite: Boolean,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + scaleIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(0.7f),
            shape = MaterialTheme.shapes.medium,
            elevation = 4.dp,
            onClick = onClick
        ) {
            Box {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        coil3.compose.AsyncImage(
                            model = book.coverUrl,
                            contentDescription = "Cover of ${book.title}",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = androidx.compose.ui.layout.ContentScale.Crop
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.subtitle1,
                            maxLines = 2,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = book.author,
                            style = MaterialTheme.typography.caption,
                            maxLines = 1,
                            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }

                // Favorite button
                IconButton(
                    onClick = onFavoriteClick,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(4.dp)
                        .size(32.dp)
                ) {
                    BookmarkIcon(
                        isFavorite = isFavorite,
                        modifier = Modifier.size(18.dp),
                    )
                }
            }
        }
    }
}