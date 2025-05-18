package features.booklist.data.mapper

import features.booklist.data.model.BookItem
import features.booklist.data.model.GoogleBooksResponse
import features.booklist.domain.model.Book

fun GoogleBooksResponse.toDomainModel(): List<Book> {
    return items.map { it.toDomainModel() }
}

fun BookItem.toDomainModel(): Book {
    // Get the best available image URL, falling back to smaller sizes if larger ones aren't available
    val coverUrl = volumeInfo.imageLinks?.let { links ->
        links.thumbnail?.replace("http:", "https:") // Ensure HTTPS
            ?: links.smallThumbnail?.replace("http:", "https:")
            ?: ""
    } ?: ""

    return Book(
        id = id,
        title = volumeInfo.title,
        authors = volumeInfo.authors ?: emptyList(),
        coverUrl = coverUrl,
        description = volumeInfo.description ?: "",
        rating = volumeInfo.rating ?: 0.0,
        publisher = volumeInfo.publisher ?: "",
        publishDate = volumeInfo.publishedDate ?: "",
        pageCount = volumeInfo.pageCount ?: 0,
        categories = volumeInfo.categories ?: emptyList()
    )
} 