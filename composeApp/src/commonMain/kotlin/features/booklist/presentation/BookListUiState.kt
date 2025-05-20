package features.booklist.presentation

import core.domain.DataState
import features.booklist.data.remote.TrendingCategory
import features.booklist.domain.model.Book

data class BookListUiState(
    val trendingBooks: TrendingBookUiState = TrendingBookUiState(),
    val searchResults: DataState<List<Book>> = DataState.Idle,
    val searchQuery: String = ""
)

data class TrendingBookUiState(
    val books: DataState<List<Book>> = DataState.Loading,
    val selectedCategory: TrendingCategory = TrendingCategory.FICTION,
)