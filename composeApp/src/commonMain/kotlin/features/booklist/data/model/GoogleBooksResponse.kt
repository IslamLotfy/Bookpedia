package features.booklist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleBooksResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<BookItem>
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo? = null,
    val accessInfo: AccessInfo? = null
)

@Serializable
data class VolumeInfo(
    val title: String,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: List<String>? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    @SerialName("averageRating")
    val rating: Double? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null
)

@Serializable
data class SaleInfo(
    val country: String? = null,
    val saleability: String? = null,
    val isEbook: Boolean? = null
)

@Serializable
data class AccessInfo(
    val country: String? = null,
    val viewability: String? = null,
    val epub: EpubInfo? = null,
    val pdf: PdfInfo? = null,
    val accessViewStatus: String? = null
)

@Serializable
data class EpubInfo(
    val isAvailable: Boolean? = null
)

@Serializable
data class PdfInfo(
    val isAvailable: Boolean? = null
) 