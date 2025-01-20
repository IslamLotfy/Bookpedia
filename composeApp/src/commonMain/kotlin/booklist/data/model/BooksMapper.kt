package booklist.data.model

import booklist.domain.model.Book

fun List<WorkDTO>.maptToDomain(): List<Book> {
    return this.map { it.mapToDomain() }
}

fun WorkDTO.mapToDomain(): Book {
    return Book(
        authorKey ?: listOf(),
        authorName ?: listOf(),
        coverKey ?: "",
        coverId ?: 0,
        firstPublishingYear ?: 0,
        ker ?: "",
        language ?: listOf(),
        title ?: "",
    )
}