package booklist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkDTO(
    @SerialName("author_key")
    val authorKey: List<String>?,
    @SerialName("author_name")
    val authorName: List<String>?,
    @SerialName("cover_edition_key")
    val coverKey: String?,
    @SerialName("cover_i")
    val coverId: Int?,
    @SerialName("first_publish_year")
    val firstPublishingYear: Int?,
    @SerialName("key")
    val ker:String?,
    @SerialName("language")
    val language: List<String>?,
    @SerialName("title")
    val title: String?,
)