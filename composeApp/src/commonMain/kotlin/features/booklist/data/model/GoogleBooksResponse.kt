package features.booklist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleBooksResponse(
    val kind: String = "books#volumes",
    val totalItems: Int = 0,
    val items: List<BookItem>? = null
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo? = null,
    val accessInfo: AccessInfo? = null,
    val searchInfo: SearchInfo? = null
)

@Serializable
data class SearchInfo(
    val textSnippet: String? = null
)

@Serializable
data class VolumeInfo(
    val title: String,
    val subtitle: String? = null,
    val authors: List<String>? = null,
    val publisher: String? = null,
    val publishedDate: String? = null,
    val description: String? = null,
    val pageCount: Int? = null,
    val categories: List<String>? = null,
    val imageLinks: ImageLinks? = null,
    val language: String? = null,
    val averageRating: Double? = null,
    val ratingsCount: Int? = null,
    val industryIdentifiers: List<IndustryIdentifier>? = null,
    val printType: String? = null,
    val maturityRating: String? = null,
    val dimensions: Dimensions? = null,
    val mainCategory: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null
)

@Serializable
data class Dimensions(
    val height: String? = null,
    val width: String? = null,
    val thickness: String? = null
)

@Serializable
data class ImageLinks(
    val smallThumbnail: String? = null,
    val thumbnail: String? = null,
    val small: String? = null,
    val medium: String? = null,
    val large: String? = null,
    val extraLarge: String? = null
)

@Serializable
data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

@Serializable
data class SaleInfo(
    val country: String = "",
    val saleability: String? = null,
    val isEbook: Boolean = false,
    val listPrice: Price? = null,
    val retailPrice: Price? = null,
    val buyLink: String? = null,
    val onSaleDate: String? = null
)

@Serializable
data class Price(
    val amount: Double,
    val currencyCode: String
)

@Serializable
data class AccessInfo(
    val country: String = "",
    val viewability: String = "",
    val embeddable: Boolean = false,
    val publicDomain: Boolean = false,
    val textToSpeechPermission: String? = null,
    val epub: FormatAvailability = FormatAvailability(false),
    val pdf: FormatAvailability = FormatAvailability(false),
    val webReaderLink: String = "",
    val accessViewStatus: String = ""
)

@Serializable
data class FormatAvailability(
    val isAvailable: Boolean,
    val downloadLink: String? = null,
    val acsTokenLink: String? = null
) 