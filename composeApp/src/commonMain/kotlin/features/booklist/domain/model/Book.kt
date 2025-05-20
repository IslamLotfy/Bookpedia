package features.booklist.domain.model

data class Book(
    val id: String,
    val title: String,
    val subtitle: String? = null,
    val authors: List<String> = emptyList(),
    val author: String,
    val description: String,
    val coverUrl: String,
    val publisher: String,
    val publishedDate: String,
    val pageCount: Int? = null,
    val categories: List<String>? = null,
    val mainCategory: String? = null,
    val language: String,
    val rating: Double? = null,
    val ratingsCount: Int = 0,
    val isbn: String,
    val printType: String? = null,
    val maturityRating: String? = null,
    val isEbook: Boolean = false,
    val saleability: String? = null,
    val buyLink: String? = null,
    val previewLink: String? = null,
    val infoLink: String? = null,
    val canonicalVolumeLink: String? = null,
    val dimensions: BookDimensions? = null
)

data class BookDimensions(
    val height: String? = null,
    val width: String? = null,
    val thickness: String? = null
) 