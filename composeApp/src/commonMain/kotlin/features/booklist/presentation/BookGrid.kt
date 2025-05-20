package features.booklist.presentation
//
//import androidx.compose.animation.*
//import androidx.compose.animation.core.*
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.lazy.grid.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Warning
//import androidx.compose.material.icons.filled.Refresh
//import androidx.compose.material.pullrefresh.PullRefreshIndicator
//import androidx.compose.material.pullrefresh.pullRefresh
//import androidx.compose.material.pullrefresh.rememberPullRefreshState
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.draw.scale
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.unit.dp
//import coil3.compose.AsyncImage
//import core.domain.AppError
//import core.theme.CardBackground
//import core.theme.TextSecondary
//import features.booklist.domain.model.Book
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun BookGrid(
//    books: List<Book>,
//    isLoading: Boolean,
//    isLoadingMore: Boolean,
//    error: AppError?,
//    onBookSelected: (Book) -> Unit,
//    onRefresh: () -> Unit,
//    onLoadMore: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val listState = androidx.compose.foundation.lazy.grid.rememberLazyGridState()
//
//    // Detect when we need to load more items
//    val shouldLoadMore = remember {
//        derivedStateOf {
//            val layoutInfo = listState.layoutInfo
//            val totalItems = layoutInfo.totalItemsCount
//            val lastVisibleItemIndex = (layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) + 1
//
//            // Load more when we reach the last 4 items
//            lastVisibleItemIndex > (totalItems - 4)
//        }
//    }
//
//    LaunchedEffect(shouldLoadMore.value) {
//        if (shouldLoadMore.value && !isLoading && !isLoadingMore && books.isNotEmpty()) {
//            onLoadMore()
//        }
//    }
//
//    var refreshing by remember { mutableStateOf(false) }
//    val pullRefreshState = rememberPullRefreshState(
//        refreshing = isLoading && books.isEmpty(),
//        onRefresh = {
//            refreshing = true
//            onRefresh()
//        }
//    )
//
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .pullRefresh(pullRefreshState)
//    ) {
//        when {
//            isLoading && books.isEmpty() -> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    CircularProgressIndicator(
//                        color = MaterialTheme.colors.primary,
//                        modifier = Modifier.size(48.dp)
//                    )
//                }
//            }
//            error != null && books.isEmpty() -> {
//                Column(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(16.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center
//                ) {
//                    val icon = when (error) {
//                        is AppError.Exception -> Icons.Filled.Warning
//                        is AppError.Server -> Icons.Filled.Warning
//                        is AppError.Business -> Icons.Filled.Warning
//                        is AppError.Frontend -> Icons.Filled.Warning
//                    }
//
//                    Icon(
//                        imageVector = icon,
//                        contentDescription = "Error icon",
//                        tint = MaterialTheme.colors.error,
//                        modifier = Modifier.size(48.dp)
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text(
//                        text = when (error) {
//                            is AppError.Exception -> {
//                                val exception = error
//                                when {
//                                    exception.errorMessage.contains("timeout", ignoreCase = true) ->
//                                        "Connection timeout. Please check your internet connection."
//                                    else -> "Error: ${exception.errorMessage}"
//                                }
//                            }
//                            is AppError.Server -> "Server error occurred. Please try again later."
//                            is AppError.Business -> "An error occurred: ${error.errorMessage}"
//                            is AppError.Frontend -> "An error occurred in the app. Please try again."
//                        },
//                        style = MaterialTheme.typography.body1,
//                        textAlign = TextAlign.Center,
//                        color = MaterialTheme.colors.error
//                    )
//
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Button(
//                        onClick = onRefresh,
//                        colors = ButtonDefaults.buttonColors(
//                            backgroundColor = MaterialTheme.colors.primary
//                        )
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.Refresh,
//                            contentDescription = "Retry",
//                            modifier = Modifier.size(16.dp)
//                        )
//                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(
//                            "Retry",
//                            style = MaterialTheme.typography.button
//                        )
//                    }
//                }
//            }
//            books.isEmpty() -> {
//                Box(
//                    modifier = Modifier.fillMaxSize(),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "No books found",
//                        style = MaterialTheme.typography.h6,
//                        textAlign = TextAlign.Center,
//                        color = TextSecondary
//                    )
//                }
//            }
//            else -> {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(2),
//                    state = listState,
//                    contentPadding = PaddingValues(12.dp),
//                    horizontalArrangement = Arrangement.spacedBy(12.dp),
//                    verticalArrangement = Arrangement.spacedBy(12.dp)
//                ) {
//                    items(
//                        items = books,
//                        key = { it.id }
//                    ) { book ->
//                        var isVisible by remember { mutableStateOf(false) }
//
//                        LaunchedEffect(true) {
//                            isVisible = true
//                        }
//
//                        AnimatedVisibility(
//                            visible = isVisible,
//                            enter = fadeIn(
//                                animationSpec = spring(
//                                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            ) + scaleIn(
//                                animationSpec = spring(
//                                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                                    stiffness = Spring.StiffnessLow
//                                )
//                            )
//                        ) {
//                            Card(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .aspectRatio(0.8f),
//                                backgroundColor = CardBackground,
//                                elevation = 4.dp,
//                                shape = RoundedCornerShape(8.dp),
//                                onClick = { onBookSelected(book) }
//                            ) {
//                                Column(
//                                    modifier = Modifier
//                                        .fillMaxSize()
//                                        .padding(8.dp),
//                                    horizontalAlignment = Alignment.CenterHorizontally,
//                                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                                ) {
//                                    AsyncImage(
//                                        model = book.coverUrl,
//                                        contentDescription = "Cover of ${book.title}",
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .weight(1f)
//                                            .clip(RoundedCornerShape(4.dp)),
//                                        contentScale = ContentScale.Crop
//                                    )
//
//                                    Column(
//                                        modifier = Modifier.fillMaxWidth(),
//                                        horizontalAlignment = Alignment.CenterHorizontally,
//                                        verticalArrangement = Arrangement.spacedBy(4.dp)
//                                    ) {
//                                        Text(
//                                            text = book.title,
//                                            style = MaterialTheme.typography.h6,
//                                            textAlign = TextAlign.Center,
//                                            maxLines = 2,
//                                            overflow = TextOverflow.Ellipsis,
//                                            color = MaterialTheme.colors.onSurface
//                                        )
//
//                                        if (book.author.isNotEmpty()) {
//                                            Text(
//                                                text = book.author,
//                                                style = MaterialTheme.typography.caption,
//                                                textAlign = TextAlign.Center,
//                                                maxLines = 1,
//                                                overflow = TextOverflow.Ellipsis,
//                                                color = TextSecondary
//                                            )
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    if (isLoadingMore) {
//                        item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .padding(16.dp),
//                                contentAlignment = Alignment.Center
//                            ) {
//                                CircularProgressIndicator(
//                                    modifier = Modifier.size(32.dp),
//                                    strokeWidth = 2.dp
//                                )
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        PullRefreshIndicator(
//            refreshing = isLoading && books.isEmpty(),
//            state = pullRefreshState,
//            modifier = Modifier.align(Alignment.TopCenter),
//            backgroundColor = MaterialTheme.colors.surface,
//            contentColor = MaterialTheme.colors.primary,
//            scale = true
//        )
//    }
//}