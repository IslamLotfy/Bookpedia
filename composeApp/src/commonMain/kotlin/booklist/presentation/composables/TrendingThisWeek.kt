package booklist.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
import booklist.domain.model.Book
import coil3.compose.AsyncImage
import core.domain.DataState

@Composable
fun TrendingThisWeekContent(viewModel: TrendingBooksViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//    BookGrid(
//        state = uiState.weeklyBooks,
//        onRetry = { viewModel.retryWeeklyBooks() }
//    )
}
