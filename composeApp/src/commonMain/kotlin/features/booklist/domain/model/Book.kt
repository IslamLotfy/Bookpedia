package features.booklist.domain.model

data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val coverUrl: String,
    val description: String,
    val rating: Double,
    val publisher: String,
    val publishDate: String,
    val pageCount: Int,
    val categories: List<String>
) 