package booklist.presentation

import androidx.lifecycle.ViewModel
import booklist.domain.usecases.GetBooksTrendingTodayUseCase

class TrendingBooksViewModel(
    private val getBooksTrendingTodayUseCase: GetBooksTrendingTodayUseCase
): ViewModel(){
    fun getTrendingBooks() = getBooksTrendingTodayUseCase.invoke()
}