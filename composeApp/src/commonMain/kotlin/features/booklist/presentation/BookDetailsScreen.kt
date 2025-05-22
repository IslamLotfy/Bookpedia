package features.booklist.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import features.booklist.domain.model.Book
import features.booklist.presentation.composables.BookDetails
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookDetailsScreen(
    bookId: String,
    onBackPressed: () -> Unit,
    viewModel: BookListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val favoriteStatusMap by viewModel.favoriteStatusMap.collectAsStateWithLifecycle()
    
    // Clear any previous selected book to prevent navigation loops
    LaunchedEffect(bookId) {
        viewModel.onClearSelectedBook()
        
        // Check if we need to fetch the book
        if (state.books.none { it.id == bookId } && 
            state.searchResults.none { it.id == bookId } &&
            state.selectedBook?.id != bookId) {
            viewModel.fetchBookById(bookId)
        }
    }
    
    // Find the book by ID from all available sources
    val book = remember(bookId, state.books, state.searchResults, state.selectedBook) {
        state.books.find { it.id == bookId } 
            ?: state.searchResults.find { it.id == bookId }
            ?: state.selectedBook?.takeIf { it.id == bookId }
    }
    
    // Show book details if available
    if (book != null) {
        BookDetails(
            book = book,
            onBackPressed = onBackPressed,
            isFavorite = favoriteStatusMap[bookId] ?: false,
            onToggleFavorite = { viewModel.toggleFavorite(book) }
        )
        
        // Check favorite status when book is available
        LaunchedEffect(bookId) {
            viewModel.checkFavoriteStatus(bookId)
        }
    }
} 