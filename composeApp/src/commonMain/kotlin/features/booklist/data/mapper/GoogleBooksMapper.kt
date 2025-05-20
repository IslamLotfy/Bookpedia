package features.booklist.data.mapper

import features.booklist.data.model.BookItem
import features.booklist.data.model.GoogleBooksResponse
import features.booklist.domain.model.Book

fun GoogleBooksResponse.toDomainModel(): List<Book> {
    return items?.map { it.toDomainModel() } ?: emptyList()
}