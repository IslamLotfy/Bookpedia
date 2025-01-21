package booklist.domain.usecases

import booklist.domain.model.Book
import booklist.domain.repository.BooksRepository
import core.domain.DataState
import kotlinx.coroutines.flow.Flow

class GetBooksTrendingThisWeekUseCase (private val booksRepository: BooksRepository){
    operator fun invoke(): Flow<DataState<List<Book>>> {
        return booksRepository.getBooksTrendingThisWeek()
    }
}