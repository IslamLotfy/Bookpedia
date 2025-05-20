package features.booklist.data.mapper

import features.booklist.data.model.BookItem
import features.booklist.domain.model.Book
import features.booklist.domain.model.BookDimensions

fun BookItem.toDomainModel(): Book {
    val volumeInfo = this.volumeInfo
    val isbn = volumeInfo.industryIdentifiers?.firstOrNull { it.type == "ISBN_13" }?.identifier
        ?: volumeInfo.industryIdentifiers?.firstOrNull { it.type == "ISBN_10" }?.identifier
        ?: ""

    // Extract dimensions if available
    val dimensions = if (volumeInfo.dimensions != null) {
        BookDimensions(
            height = volumeInfo.dimensions.height,
            width = volumeInfo.dimensions.width,
            thickness = volumeInfo.dimensions.thickness
        )
    } else null

    return Book(
        id = this.id,
        title = volumeInfo.title,
        subtitle = volumeInfo.subtitle,
        authors = volumeInfo.authors ?: emptyList(),
        author = volumeInfo.authors?.joinToString(", ") ?: "Unknown",
        description = volumeInfo.description ?: "",
        coverUrl = volumeInfo.imageLinks?.thumbnail?.replace("http:", "https:") ?: "",
        publisher = volumeInfo.publisher ?: "",
        publishedDate = volumeInfo.publishedDate ?: "",
        pageCount = volumeInfo.pageCount,
        categories = volumeInfo.categories,
        mainCategory = volumeInfo.mainCategory,
        language = volumeInfo.language ?: "",
        rating = volumeInfo.averageRating,
        ratingsCount = volumeInfo.ratingsCount ?: 0,
        isbn = isbn,
        printType = volumeInfo.printType,
        maturityRating = volumeInfo.maturityRating,
        isEbook = this.saleInfo?.isEbook ?: false,
        saleability = this.saleInfo?.saleability,
        buyLink = this.saleInfo?.buyLink,
        previewLink = volumeInfo.previewLink,
        infoLink = volumeInfo.infoLink,
        canonicalVolumeLink = volumeInfo.canonicalVolumeLink,
        dimensions = dimensions
    )
} 