package features.booklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.AppError
import core.domain.DataState
import features.booklist.data.remote.TrendingCategory
import features.booklist.domain.model.Book
import features.booklist.domain.usecase.GetTrendingBooksUseCase
import features.booklist.domain.usecase.SearchBooksUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(private val getTrendingBooksUseCase: GetTrendingBooksUseCase,
    private val searchBooksUseCase: SearchBooksUseCase) : ViewModel() {

    private val _state = MutableStateFlow(BookListState())
    val state: StateFlow<BookListState> = _state.asStateFlow()

    private var trendingCurrentPage = 0
    private var searchCurrentPage = 0
    private var isLoadingMoreTrending = false
    private var isLoadingMoreSearch = false
    private var hasMoreTrendingItems = true
    private var hasMoreSearchItems = true

    init {
        fetchTrendingBooks()
    }

    fun fetchTrendingBooks(
        category: TrendingCategory = _state.value.selectedCategory,
        reset: Boolean = true
    ) {
        if (reset) {
            trendingCurrentPage = 0
            hasMoreTrendingItems = true
            _state.update {
                it.copy(
                    isLoading = true,
                    books = emptyList(),
                    error = null
                )
            }
        } else if (isLoadingMoreTrending || !hasMoreTrendingItems) {
            return
        } else {
            isLoadingMoreTrending = true
        }

        viewModelScope.launch {
           getTrendingBooksUseCase(
                category = category,
                page = trendingCurrentPage,
                pageSize = PAGE_SIZE
            ).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        if (reset) {
                            _state.update { it.copy(isLoading = true, error = null) }
                        } else {
                            _state.update { it.copy(isLoadingMore = true, error = null) }
                        }
                    }
                    is DataState.Success -> {
                        val newBooks = result.data
                        hasMoreTrendingItems = newBooks.isNotEmpty() && newBooks.size >= PAGE_SIZE

                        _state.update { currentState ->
                            val updatedBooks = if (reset) {
                                newBooks
                            } else {
                                currentState.books + newBooks
                            }

                            currentState.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                books = updatedBooks,
                                error = null,
                                selectedCategory = category
                            )
                        }

                        trendingCurrentPage++
                        isLoadingMoreTrending = false
                    }
                    is DataState.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                isLoadingMore = false,
                                error = result.error
                            )
                        }
                        isLoadingMoreTrending = false
                    }
                    is DataState.Idle -> {
                        _state.update { it.copy(isLoading = false, isLoadingMore = false) }
                        isLoadingMoreTrending = false
                    }
                }
            }
        }
    }

    fun searchBooks(
        query: String,
        reset: Boolean = true
    ) {
        if (query.isBlank()) {
            _state.update { it.copy(
                searchResults = emptyList(),
                isSearching = false,
                searchQuery = ""
            )}
            return
        }

        if (reset) {
            searchCurrentPage = 0
            hasMoreSearchItems = true
            _state.update {
                it.copy(
                    isSearchLoading = true,
                    searchResults = emptyList(),
                    searchError = null,
                    searchQuery = query,
                    isSearching = true
                )
            }
        } else if (isLoadingMoreSearch || !hasMoreSearchItems) {
            return
        } else {
            isLoadingMoreSearch = true
        }

        viewModelScope.launch {
           searchBooksUseCase(
                query = query,
                page = searchCurrentPage,
                pageSize = PAGE_SIZE
            ).collect { result ->
                when (result) {
                    is DataState.Loading -> {
                        if (reset) {
                            _state.update { it.copy(isSearchLoading = true, searchError = null) }
                        } else {
                            _state.update { it.copy(isSearchLoadingMore = true, searchError = null) }
                        }
                    }
                    is DataState.Success -> {
                        val newBooks = result.data
                        hasMoreSearchItems = newBooks.isNotEmpty() && newBooks.size >= PAGE_SIZE

                        _state.update { currentState ->
                            val updatedBooks = if (reset) {
                                newBooks
                            } else {
                                currentState.searchResults + newBooks
                            }

                            currentState.copy(
                                isSearchLoading = false,
                                isSearchLoadingMore = false,
                                searchResults = updatedBooks,
                                searchError = null,
                                isSearching = true
                            )
                        }

                        searchCurrentPage++
                        isLoadingMoreSearch = false
                    }
                    is DataState.Error -> {
                        _state.update {
                            it.copy(
                                isSearchLoading = false,
                                isSearchLoadingMore = false,
                                searchError = result.error
                            )
                        }
                        isLoadingMoreSearch = false
                    }
                    is DataState.Idle -> {
                        _state.update {
                            it.copy(isSearchLoading = false, isSearchLoadingMore = false)
                        }
                        isLoadingMoreSearch = false
                    }
                }
            }
        }
    }

    fun loadMoreSearchResults() {
        if (!isLoadingMoreSearch && hasMoreSearchItems) {
            searchBooks(_state.value.searchQuery, reset = false)
        }
    }

    fun loadMoreTrendingBooks() {
        if (!isLoadingMoreTrending && hasMoreTrendingItems) {
            fetchTrendingBooks(reset = false)
        }
    }

    fun clearSearch() {
        _state.update { it.copy(
            searchResults = emptyList(),
            isSearching = false,
            searchQuery = ""
        )}
    }

    fun onCategorySelected(category: TrendingCategory) {
        if (category == _state.value.selectedCategory) return

        _state.update { it.copy(selectedCategory = category) }
        fetchTrendingBooks(category)
    }

    fun onBookSelected(book: Book) {
        _state.update { it.copy(selectedBook = book) }
    }

    fun onClearSelectedBook() {
        _state.update { it.copy(selectedBook = null) }
    }

    data class BookListState(
        val isLoading: Boolean = false,
        val isLoadingMore: Boolean = false,
        val books: List<Book> = emptyList(),
        val error: AppError? = null,
        val selectedCategory: TrendingCategory = TrendingCategory.FICTION,

        val isSearching: Boolean = false,
        val searchQuery: String = "",
        val isSearchLoading: Boolean = false,
        val isSearchLoadingMore: Boolean = false,
        val searchResults: List<Book> = emptyList(),
        val searchError: AppError? = null,

        val selectedBook: Book? = null
    )

    companion object {
        private const val PAGE_SIZE = 20
    }
}