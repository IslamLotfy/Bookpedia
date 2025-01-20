package booklist.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingBooksResponse(
    @SerialName("works")
    val books: List<WorkDTO>
)