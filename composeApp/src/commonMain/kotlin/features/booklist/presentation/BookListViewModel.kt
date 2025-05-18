package features.booklist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.AppError
import core.domain.DataState
import features.booklist.data.remote.TrendingCategory
import features.booklist.data.remote.TrendingTimeRange
import features.booklist.domain.model.Book
import features.booklist.domain.usecase.BookUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.HttpRequestTimeoutException


class BookListViewModel(
    private val bookUseCases: BookUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadTrendingBooks()
    }

    fun loadTrendingBooks(
        category: TrendingCategory = _uiState.value.trendingBooks.selectedCategory,
        timeRange: TrendingTimeRange = _uiState.value.trendingBooks.selectedTimeRange
    ) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    trendingBooks = it.trendingBooks.copy(
                        selectedCategory = category,
                        selectedTimeRange = timeRange
                    )
                )
            }
            bookUseCases.getTrendingBooks(category, timeRange).collect { state ->
                _uiState.update {
                    it.copy(
                        trendingBooks = it.trendingBooks.copy(
                            books = state
                        )
                    )
                }
            }
        }
    }

    fun updateCategory(category: TrendingCategory) {
        loadTrendingBooks(category = category)
    }

    fun updateTimeRange(timeRange: TrendingTimeRange) {
        loadTrendingBooks(timeRange = timeRange)
    }

    fun refresh() {
        loadTrendingBooks()
    }

    fun retryTrendingBooks() {
        getTrendingBooks()
    }

    fun refreshTrendingBooks() {
        getTrendingBooks()
    }

    fun retrySearch() {
        searchBooks(_uiState.value.searchQuery)
    }

    private fun getTrendingBooks() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    trendingBooks = it.trendingBooks.copy(
                        books = DataState.Loading
                    ),
                )
            }
            try {
                bookUseCases.getTrendingBooks().collect { state ->
                    _uiState.update {
                        it.copy(
                            trendingBooks = it.trendingBooks.copy(
                                books = state
                            ),
                        )
                    }
                }
            } catch (e: HttpRequestTimeoutException) {
                _uiState.update {
                    it.copy(
                        trendingBooks = it.trendingBooks.copy(
                            books = DataState.Error(AppError.Exception("Request timed out. Please check your internet connection.")
                            )
                        )
                    )
                }
            } catch (e: ClientRequestException) {
                _uiState.update {
                    it.copy(
                        trendingBooks = it.trendingBooks.copy(
                            books = DataState.Error(AppError.Business(e.message)
                            )
                        )
                    )
                }
            } catch (e: ServerResponseException) {
                _uiState.update {
                    it.copy(
                        trendingBooks = it.trendingBooks.copy(
                            books = DataState.Error(AppError.Server(e.message))
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        trendingBooks = it.trendingBooks.copy(
                            books = DataState.Error(AppError.Exception(e.message ?: "Unknown error occurred")
                            )
                        )
                    )
                }
            }
        }
    }

    fun searchBooks(query: String) {
        if (query.isBlank()) {
            _uiState.update {
                it.copy(
                    searchQuery = "",
                    searchResults = DataState.Idle
                )
            }
            return
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    searchQuery = query,
                    searchResults = DataState.Loading
                )
            }
            try {
                bookUseCases.searchBooks(query).collect { state ->
                    _uiState.update { it.copy(searchResults = state) }
                }
            } catch (e: HttpRequestTimeoutException) {
                _uiState.update {
                    it.copy(searchResults = DataState.Error(AppError.Exception("Request timed out. Please check your internet connection.")))
                }
            } catch (e: ClientRequestException) {
                _uiState.update {
                    it.copy(searchResults = DataState.Error(AppError.Business(e.message)))
                }
            } catch (e: ServerResponseException) {
                _uiState.update {
                    it.copy(searchResults = DataState.Error(AppError.Server(e.message)))
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(searchResults = DataState.Error(AppError.Exception(e.message ?: "Unknown error occurred")))
                }
            }
        }
    }
}