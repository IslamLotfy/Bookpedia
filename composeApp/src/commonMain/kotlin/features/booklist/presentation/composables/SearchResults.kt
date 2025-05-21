package features.booklist.presentation.composables

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import core.domain.AppError
import features.booklist.domain.model.Book
import features.booklist.presentation.BookListViewModel
import features.booklist.presentation.ErrorContent
import features.booklist.presentation.BookGridItem

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SearchResults(
    books: List<Book>,
    isLoading: Boolean,
    error: AppError?,
    onBookClicked: (Book) -> Unit,
    onLoadMore: () -> Unit,
    modifier: Modifier = Modifier
) {
    val gridState = rememberLazyGridState()


    LaunchedEffect(gridState.canScrollForward) {
        if (!gridState.canScrollForward && !isLoading && books.isNotEmpty()) {
            onLoadMore()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading && books.isEmpty() -> {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
            error != null && books.isEmpty() -> {
                ErrorContent(error = error, onRetry = onLoadMore, modifier = Modifier.fillMaxSize())
            }
            books.isEmpty() -> {
                Text(
                    text = "No results found",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState,
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = books,
                        key = { it.id }
                    ) { book ->
                        BookGridItem(
                            book = book,
                            onClick = { onBookClicked(book) }
                        )
                    }

                    if (isLoading) {
                        item(span = { GridItemSpan(2) }) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp),
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchResultItem(
    book: Book,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(book) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            elevation = 2.dp,
            onClick = onClick
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = book.coverUrl,
                    contentDescription = "Cover of ${book.title}",
                    modifier = Modifier
                        .width(60.dp)
                        .height(90.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = book.title,
                        style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = book.author,
                        style = MaterialTheme.typography.body2,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    if (book.rating != null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "${book.rating}",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.primary
                            )
                            Text(
                                text = " • ${book.publishedDate}",
                                style = MaterialTheme.typography.caption,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                            )
                        }
                    } else {
                        Text(
                            text = book.publishedDate,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                        )
                    }
                }
            }
        }
    }
}