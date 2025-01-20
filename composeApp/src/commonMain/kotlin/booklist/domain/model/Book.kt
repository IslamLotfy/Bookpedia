package booklist.domain.model

data class Book(
    val authorKey: List<String>,
    val authorName: List<String>,
    val coverKey: String,
    val coverId: Int,
    val firstPublishingYear: Int,
    val ker:String,
    val language: List<String>,
    val title: String,
)