package booklist.presentation.composables

import androidx.compose.runtime.Composable
import booklist.presentation.TrendingBooksViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import booklist.domain.model.Book
import coil3.compose.AsyncImage
import core.domain.DataState

@Composable
fun TrendingThisMonthContent(viewModel: TrendingBooksViewModel) {
    val books =
        viewModel.trnedingThisMonthScreenState.collectAsStateWithLifecycle(initialValue = DataState.Idle)

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        when (val state = books.value) {
            is DataState.Error,
            DataState.Idle,
            DataState.Loading -> Text(state.toString())

            is DataState.Success<List<Book>> -> {
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
                            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.FillBounds,
                            clipToBounds = true
                        )
                    }
                }

            }
        }
    }
}