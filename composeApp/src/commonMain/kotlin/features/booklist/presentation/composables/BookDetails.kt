package features.booklist.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import features.booklist.presentation.composables.BookmarkIcon
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import features.booklist.domain.model.Book

@Composable
fun BookDetails(
    book: Book,
    onBackPressed: () -> Unit,
    isFavorite: Boolean = false,
    onToggleFavorite: (Book) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = modifier.fillMaxSize()) {
        // Background blur image
        AsyncImage(
            model = book.coverUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .blur(20.dp),
            contentScale = ContentScale.Crop,
            onSuccess = { isLoading = false }
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.7f),
                            Color.Black.copy(alpha = 0.9f)
                        )
                    )
                )
        )

        // Transparent app bar
        TopAppBar(
            title = {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = { onToggleFavorite(book) }) {
                    BookmarkIcon(
                        isFavorite = isFavorite,
                        modifier = Modifier.size(24.dp)
                    )

                }
            },
            backgroundColor = Color.Black.copy(alpha = 0.2f),
            elevation = 0.dp,
            modifier = Modifier
                .statusBarsPadding()
                .zIndex(1f)
        )

        // Content
        AnimatedVisibility(
            visible = !isLoading,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(top = 56.dp) // Account for TopAppBar height
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Book cover with shadow
                Surface(
                    modifier = Modifier
                        .width(180.dp)
                        .height(270.dp),
                    elevation = 16.dp,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    AsyncImage(
                        model = book.coverUrl,
                        contentDescription = "Cover of ${book.title}",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Title with glow effect
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Author
                Text(
                    text = "by ${book.author}",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Rating
                if (book.rating != null) {
                    Surface(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFD700),
                                modifier = Modifier.size(20.dp)
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "${book.rating}",
                                style = MaterialTheme.typography.body1,
                                color = Color.White
                            )

                            Spacer(modifier = Modifier.width(4.dp))

                            Text(
                                text = "(${book.ratingsCount} ratings)",
                                style = MaterialTheme.typography.body2,
                                color = Color.White.copy(alpha = 0.7f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Reading statistics
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "${book.ratingsCount ?: 0} readings",
                            style = MaterialTheme.typography.caption,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Book details in a card
                Surface(
                    color = MaterialTheme.colors.primary.copy(alpha = 0.15f),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    BookInfoRow(
                        items = listOfNotNull(
                            book.publisher.takeIf { it.isNotBlank() }?.let { "Publisher: $it" },
                            book.publishedDate.takeIf { it.isNotBlank() }?.let { "Published: $it" },
                            book.pageCount?.let { "Pages: $it" },
                            book.isbn.takeIf { it.isNotBlank() }?.let { "ISBN: $it" }
                        ),
                        modifier = Modifier.padding(vertical = 12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Categories moved above description
                if (!book.categories.isNullOrEmpty()) {
                    FlowRow(
                        mainAxisSpacing = 8.dp,
                        crossAxisSpacing = 8.dp,
                        mainAxisAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        book.categories.forEach { category ->
                            Chip(
                                text = category
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }

                // Description without label
                if (book.description.isNotBlank()) {
                    Surface(
                        color = Color.White.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = book.description,
                            style = MaterialTheme.typography.body1,
                            color = Color.White.copy(alpha = 0.9f),
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }

        // Loading indicator
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colors.primary)
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    mainAxisSpacing: Dp = 0.dp,
    crossAxisSpacing: Dp = 0.dp,
    mainAxisAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val rows = mutableListOf<MeasuredRow>()
        val rowConstraints = constraints.copy(minWidth = 0)

        var currentRow = MeasuredRow(0, 0)
        rows.add(currentRow)

        measurables.forEach { measurable ->
            val placeable = measurable.measure(rowConstraints)

            if (currentRow.width > 0f && currentRow.width + placeable.width + mainAxisSpacing.roundToPx() > constraints.maxWidth) {
                currentRow = MeasuredRow(0, currentRow.maxHeight + crossAxisSpacing.roundToPx())
                rows.add(currentRow)
            }

            val placeableWidth = placeable.width
            val placeableHeight = placeable.height
            currentRow.placeables.add(placeable)
            currentRow.width += placeableWidth + if (currentRow.placeables.size > 1) mainAxisSpacing.roundToPx() else 0
            currentRow.maxHeight = maxOf(currentRow.maxHeight, placeableHeight)
        }

        val totalHeight = rows.sumOf { it.maxHeight } + (rows.size - 1).coerceAtLeast(0) * crossAxisSpacing.roundToPx()

        layout(
            width = constraints.maxWidth,
            height = totalHeight
        ) {
            var y = 0

            rows.forEach { row ->
                val rowWidth = row.width - if (row.placeables.isNotEmpty()) mainAxisSpacing.roundToPx() else 0

                // Calculate starting x based on alignment
                var x = when (mainAxisAlignment) {
                    Alignment.CenterHorizontally -> (constraints.maxWidth - rowWidth) / 2
                    Alignment.End -> constraints.maxWidth - rowWidth
                    else -> 0
                }

                row.placeables.forEach { placeable ->
                    placeable.place(x = x, y = y)
                    x += placeable.width + mainAxisSpacing.roundToPx()
                }

                y += row.maxHeight + crossAxisSpacing.roundToPx()
            }
        }
    }
}

private class MeasuredRow(
    var width: Int = 0,
    var y: Int = 0,
    var maxHeight: Int = 0,
    val placeables: MutableList<Placeable> = mutableListOf()
)

@Composable
fun BookInfoRow(
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items.forEach { item ->
            Text(
                text = item,
                style = MaterialTheme.typography.body2,
                color = Color.White.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 2.dp)
            )
        }
    }
}

@Composable
fun Chip(
    text: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colors.primary.copy(alpha = 0.7f),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.caption,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
} 