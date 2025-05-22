package features.booklist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import features.booklist.domain.model.Book

@Entity(tableName = "favorite_books")
data class FavoriteBookEntity(
    @PrimaryKey val id: String,
    val title: String,
    val subtitle: String?,
    val author: String,
    val description: String,
    val coverUrl: String,
    val publisher: String,
    val publishedDate: String,
    val pageCount: Int?,
    val mainCategory: String?,
    val language: String,
    val rating: Double?,
    val ratingsCount: Int,
    val isbn: String,
    val isFavorite: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

fun FavoriteBookEntity.toBook() = Book(
    id = id,
    title = title,
    subtitle = subtitle,
    author = author,
    authors = listOf(author), // Simplified as we don't store full author list
    description = description,
    coverUrl = coverUrl,
    publisher = publisher,
    publishedDate = publishedDate,
    pageCount = pageCount,
    categories = mainCategory?.let { listOf(it) },
    mainCategory = mainCategory,
    language = language,
    rating = rating,
    ratingsCount = ratingsCount,
    isbn = isbn
)

fun Book.toFavoriteBookEntity() = FavoriteBookEntity(
    id = id,
    title = title,
    subtitle = subtitle,
    author = author,
    description = description,
    coverUrl = coverUrl,
    publisher = publisher,
    publishedDate = publishedDate,
    pageCount = pageCount,
    mainCategory = mainCategory,
    language = language, 
    rating = rating,
    ratingsCount = ratingsCount,
    isbn = isbn
) 