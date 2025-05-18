package booklist.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import booklist.domain.model.Book
import booklist.domain.usecases.GetBooksTrendingThisMonthUseCase
import booklist.domain.usecases.GetBooksTrendingThisWeekUseCase
import booklist.domain.usecases.GetBooksTrendingThisYearUseCase
import booklist.domain.usecases.GetBooksTrendingTodayUseCase
import core.domain.AppError
import core.domain.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TrendingBooksUiState(
    val todayBooks: DataState<List<Book>> = DataState.Idle,
    val weeklyBooks: DataState<List<Book>> = DataState.Idle,
    val monthlyBooks: DataState<List<Book>> = DataState.Idle,
    val yearlyBooks: DataState<List<Book>> = DataState.Idle
)

class TrendingBooksViewModel(
    private val getBooksTrendingTodayUseCase: GetBooksTrendingTodayUseCase,
    private val getBooksTrendingThisWeekUseCase: GetBooksTrendingThisWeekUseCase,
    private val getBooksTrendingThisMonthUseCase: GetBooksTrendingThisMonthUseCase,
    private val getBooksTrendingThisYearUseCase: GetBooksTrendingThisYearUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow(TrendingBooksUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadAllData()
    }

    private fun loadAllData() {
        getTrendingTodayBooks()
        getTrendingThisWeekBooks()
        getTrendingThisMonthBooks()
        getTrendingThisYearBooks()
    }

    fun retryAll() {
        loadAllData()
    }

    fun retryTodayBooks() {
        getTrendingTodayBooks()
    }

    fun retryWeeklyBooks() {
        getTrendingThisWeekBooks()
    }

    fun retryMonthlyBooks() {
        getTrendingThisMonthBooks()
    }

    fun retryYearlyBooks() {
        getTrendingThisYearBooks()
    }

    private fun getTrendingTodayBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(todayBooks = DataState.Loading) }
            try {
                getBooksTrendingTodayUseCase.invoke().collect { state ->
                    _uiState.update { it.copy(todayBooks = state) }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(todayBooks = DataState.Error(AppError.Exception(e)))
                }
            }
        }
    }

    private fun getTrendingThisWeekBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(weeklyBooks = DataState.Loading) }
            try {
                getBooksTrendingThisWeekUseCase.invoke().collect { state ->
                    _uiState.update { it.copy(weeklyBooks = state) }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(weeklyBooks = DataState.Error(AppError.Exception(e)))
                }
            }
        }
    }

    private fun getTrendingThisMonthBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(monthlyBooks = DataState.Loading) }
            try {
                getBooksTrendingThisMonthUseCase.invoke().collect { state ->
                    _uiState.update { it.copy(monthlyBooks = state) }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(monthlyBooks = DataState.Error(AppError.Exception(e)))
                }
            }
        }
    }

    private fun getTrendingThisYearBooks() {
        viewModelScope.launch {
            _uiState.update { it.copy(yearlyBooks = DataState.Loading) }
            try {
                getBooksTrendingThisYearUseCase.invoke().collect { state ->
                    _uiState.update { it.copy(yearlyBooks = state) }
                }
            } catch (e: Exception) {
                _uiState.update { 
                    it.copy(yearlyBooks = DataState.Error(AppError.Exception(e)))
                }
            }
        }
    }
}