package booklist.domain.repository

import booklist.domain.model.Book
import core.domain.DataState
import kotlinx.coroutines.flow.Flow

interface BooksRepository {
    fun getBooksTrendingToday(): Flow<DataState<List<Book>>>
    fun getBooksTrendingThisWeek(): Flow<DataState<List<Book>>>
    fun getBooksTrendingThisMonth(): Flow<DataState<List<Book>>>
    fun getBooksTrendingThisYear(): Flow<DataState<List<Book>>>
}