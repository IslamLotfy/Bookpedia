package booklist.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import booklist.domain.model.Book
import booklist.domain.usecases.GetBooksTrendingThisMonthUseCase
import booklist.domain.usecases.GetBooksTrendingThisWeekUseCase
import booklist.domain.usecases.GetBooksTrendingThisYearUseCase
import booklist.domain.usecases.GetBooksTrendingTodayUseCase
import core.domain.DataState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TrendingBooksViewModel(
    private val getBooksTrendingTodayUseCase: GetBooksTrendingTodayUseCase,
    private val getBooksTrendingThisWeekUseCase: GetBooksTrendingThisWeekUseCase,
    private val getBooksTrendingThisMonthUseCase: GetBooksTrendingThisMonthUseCase,
    private val getBooksTrendingThisYearUseCase: GetBooksTrendingThisYearUseCase
): ViewModel(){
    private val _trnedingTodayScreenState: MutableStateFlow<DataState<List<Book>>> = MutableStateFlow(DataState.Idle)
    val trnedingTodayScreenState = _trnedingTodayScreenState.asStateFlow()
    private val _trnedingThisMonthScreenState: MutableStateFlow<DataState<List<Book>>> = MutableStateFlow(DataState.Idle)
    val trnedingThisMonthScreenState = _trnedingThisMonthScreenState.asStateFlow()
    private val _trnedingThisWeekScreenState: MutableStateFlow<DataState<List<Book>>> = MutableStateFlow(DataState.Idle)
    val trnedingThisWeekScreenState = _trnedingThisWeekScreenState.asStateFlow()
    private val _trnedingThisYearScreenState: MutableStateFlow<DataState<List<Book>>> = MutableStateFlow(DataState.Idle)
    val trnedingThisYearScreenState = _trnedingThisYearScreenState.asStateFlow()


    init {
        getTrendingTodayBooks()
        getTrendingThisWeekBooks()
        getTrendingThisMonthBooks()
        getTrendingThisYearBooks()
    }
    private fun getTrendingTodayBooks() {
        viewModelScope.launch {
            getBooksTrendingTodayUseCase.invoke().collect{state->
                _trnedingTodayScreenState.update {
                    state
                }
            }
        }
    }
    private fun getTrendingThisWeekBooks() {
        viewModelScope.launch {
            getBooksTrendingTodayUseCase.invoke().collect{state->
                _trnedingThisWeekScreenState.update {
                    state
                }
            }
        }
    }
    private fun getTrendingThisMonthBooks() {
        viewModelScope.launch {
            getBooksTrendingTodayUseCase.invoke().collect{state->
                _trnedingThisMonthScreenState.update {
                    state
                }
            }
        }
    }
    private fun getTrendingThisYearBooks() {
        viewModelScope.launch {
            getBooksTrendingTodayUseCase.invoke().collect{state->
                _trnedingThisYearScreenState.update {
                    state
                }
            }
        }
    }
}