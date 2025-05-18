package booklist.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import booklist.domain.model.Book
import coil3.compose.AsyncImage
import core.domain.AppError
import core.domain.DataState

@Composable
fun BookGrid(
    state: DataState<List<Book>>,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is DataState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = when (state.error) {
                            is AppError.Exception -> {
                                val exception = (state.error as AppError.Exception).exception
                                when {
                                    exception.message?.contains("timeout", ignoreCase = true) == true -> 
                                        "Connection timeout. Please check your internet connection."
                                    else -> "Error: ${exception.message ?: "Unknown error occurred"}"
                                }
                            }
                            is AppError.Server -> "Server error occurred. Please try again later."
                            is AppError.Business -> "An error occurred: ${state.error.errorTag}"
                            AppError.Frontend -> "An error occurred in the app. Please try again."
                        },
                        textAlign = TextAlign.Center
                    )
                    Button(onClick = onRetry) {
                        Text("Retry")
                    }
                }
            }
            DataState.Idle -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
            DataState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
            is DataState.Success -> {
                if (state.data.isEmpty()) {
                    Text(
                        text = "No books found",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(12.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { book ->
                            AsyncImage(
                                model = "https://covers.openlibrary.org/b/id/${book.coverId}-L.jpg",
                                contentDescription = "book cover",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.FillBounds,
                                clipToBounds = true
                            )
                        }
                    }
                }
            }
        }
    }
}