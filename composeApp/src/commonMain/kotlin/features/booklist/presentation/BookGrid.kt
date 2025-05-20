package features.booklist.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import core.domain.AppError
import core.domain.DataState
import core.theme.CardBackground
import core.theme.TextSecondary
import features.booklist.domain.model.Book

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookGrid(
    state: DataState<List<Book>>,
    onBookSelected: (Book) -> Unit,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {},
    onRefresh: () -> Unit = {}
) {
    var refreshing by remember { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = {
            refreshing = true
            onRefresh()
        }
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .pullRefresh(pullRefreshState)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (state) {
                is DataState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val icon = when (state.error) {
                            is AppError.Exception -> Icons.Filled.Warning
                            is AppError.Server -> Icons.Filled.Warning
                            is AppError.Business -> Icons.Filled.Warning
                            is AppError.Frontend -> Icons.Filled.Warning
                        }

                        Icon(
                            imageVector = icon,
                            contentDescription = "Error icon",
                            tint = MaterialTheme.colors.error,
                            modifier = Modifier.size(48.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = when (state.error) {
                                is AppError.Exception -> {
                                    val exception = state.error
                                    when {
                                        exception.errorMessage.contains("timeout", ignoreCase = true) ->
                                            "Connection timeout. Please check your internet connection."
                                        else -> "Error: ${exception.errorMessage}"
                                    }
                                }
                                is AppError.Server -> "Server error occurred. Please try again later."
                                is AppError.Business -> "An error occurred: ${state.error.errorMessage}"
                                is AppError.Frontend -> "An error occurred in the app. Please try again."
                            },
                            style = MaterialTheme.typography.body1,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.error
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = onRetry,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "Retry",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Retry",
                                style = MaterialTheme.typography.button
                            )
                        }
                    }
                }
                DataState.Idle, DataState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colors.primary,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
                is DataState.Success -> {
                    refreshing = false
                    if (state.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No books found",
                                style = MaterialTheme.typography.h6,
                                textAlign = TextAlign.Center,
                                color = TextSecondary
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = state.data,
                                key = { it.id }
                            ) { book ->
                                var isVisible by remember { mutableStateOf(false) }
                                
                                LaunchedEffect(true) {
                                    isVisible = true
                                }

                                AnimatedVisibility(
                                    visible = isVisible,
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
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(0.8f),
                                        backgroundColor = CardBackground,
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(8.dp),
                                        onClick = { onBookSelected(book) }
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .padding(8.dp),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            AsyncImage(
                                                model = book.coverUrl,
                                                contentDescription = "Cover of ${book.title}",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .weight(1f)
                                                    .clip(RoundedCornerShape(4.dp)),
                                                contentScale = ContentScale.Crop
                                            )

                                            Column(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(4.dp)
                                            ) {
                                                Text(
                                                    text = book.title,
                                                    style = MaterialTheme.typography.h6,
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis,
                                                    color = MaterialTheme.colors.onSurface
                                                )

                                                if (book.authors.isNotEmpty()) {
                                                    Text(
                                                        text = book.authors.first(),
                                                        style = MaterialTheme.typography.caption,
                                                        textAlign = TextAlign.Center,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        color = TextSecondary
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = MaterialTheme.colors.primary,
            scale = true
        )
    }
}