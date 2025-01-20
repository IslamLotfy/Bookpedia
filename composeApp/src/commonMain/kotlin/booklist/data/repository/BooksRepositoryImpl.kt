package booklist.data.repository

import booklist.data.model.maptToDomain
import booklist.data.remoteDS.BooksRemoteDataSource
import booklist.domain.model.Book
import booklist.domain.repository.BooksRepository
import core.domain.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BooksRepositoryImpl(private val booksRemoteDataSource: BooksRemoteDataSource):BooksRepository{
    override fun getBooksTrendingToday(): Flow<DataState<List<Book>>>{
        return flow {
            val books = booksRemoteDataSource.getBooksTrendingToday().maptToDomain()
            emit(DataState.Success(books))
        }
    }

    override fun getBooksTrendingThisWeek(): Flow<DataState<List<Book>>> {
        return flow {
             val books = booksRemoteDataSource.getBooksTrendingThisWeek().maptToDomain()
            emit(DataState.Success(books))
        }
    }

    override fun getBooksTrendingThisMonth(): Flow<DataState<List<Book>>>{
       return flow {
            val books = booksRemoteDataSource.getBooksTrendingThisMonth().maptToDomain()
           emit(DataState.Success(books))
       }
    }

    override fun getBooksTrendingThisYear(): Flow<DataState<List<Book>>>{
       return flow {
            val books = booksRemoteDataSource.getBooksTrendingThisYear().maptToDomain()
            emit(DataState.Success(books))
       }
    }

}
