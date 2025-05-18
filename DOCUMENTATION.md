# 📚 Bookpedia Documentation

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [Features](#features)
4. [Implementation Details](#implementation-details)
5. [State Management](#state-management)
6. [UI Components](#ui-components)
7. [Data Flow](#data-flow)
8. [Error Handling](#error-handling)
9. [API Integration](#api-integration)
10. [Testing](#testing)

## Overview

Bookpedia is a Kotlin Multiplatform Mobile (KMM) application that showcases trending books using the Google Books API. The app demonstrates modern Android development practices, clean architecture, and material design principles.

## Architecture

### Clean Architecture Implementation

The app follows a three-layer Clean Architecture approach:

```
Presentation Layer (UI) → Domain Layer (Business Logic) → Data Layer (API/Storage)
```

#### 1. Presentation Layer
```kotlin
class BookListViewModel(
    private val bookUseCases: BookUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(BookListUiState())
    val uiState = _uiState.asStateFlow()
    
    // UI state management and user interactions
}
```

#### 2. Domain Layer
```kotlin
class GetTrendingBooksUseCase(
    private val repository: BooksRepository
) {
    operator fun invoke(
        category: TrendingCategory,
        timeRange: TrendingTimeRange
    ): Flow<DataState<List<Book>>>
}
```

#### 3. Data Layer
```kotlin
class BooksRepositoryImpl(
    private val remoteDataSource: GoogleBooksRemoteDataSource
) : BooksRepository {
    // Implementation of repository interface
}
```

### Data Flow
```
UI → ViewModel → UseCase → Repository → RemoteDataSource → API
```

## Features

### 1. Trending Books Display

#### Time-based Categories
```kotlin
enum class TrendingTimeRange(val maxResults: Int, val orderBy: String) {
    THIS_WEEK(20, "newest"),
    THIS_MONTH(30, "newest"),
    THIS_YEAR(40, "relevance")
}
```

#### Book Categories
```kotlin
enum class TrendingCategory(val query: String) {
    FICTION("subject:fiction"),
    NON_FICTION("subject:non-fiction"),
    BUSINESS("subject:business"),
    // ... other categories
}
```

### 2. Book Details

The BookDetails screen displays comprehensive information about a selected book:

```kotlin
@Composable
fun BookDetails(
    book: Book,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Book details implementation
}
```

## Implementation Details

### 1. State Management

#### UI State
```kotlin
data class BookListUiState(
    val books: DataState<List<Book>> = DataState.Loading,
    val selectedCategory: TrendingCategory = TrendingCategory.FICTION,
    val selectedTimeRange: TrendingTimeRange = TrendingTimeRange.THIS_MONTH
)
```

#### Data State
```kotlin
sealed class DataState<out T> {
    object Loading : DataState<Nothing>()
    object Idle : DataState<Nothing>()
    data class Success<T>(val data: T) : DataState<T>()
    data class Error(val error: AppError) : DataState<Nothing>()
}
```

### 2. Error Handling

```kotlin
sealed class AppError {
    data class Business(val errorMessage: String) : AppError()
    data class Server(val errorMessage: String) : AppError()
    data class Frontend(val errorMessage: String) : AppError()
    data class Exception(val errorMessage: String) : AppError()
}
```

### 3. Network Layer

#### HTTP Client Configuration
```kotlin
class GoogleBooksHttpClient {
    val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
        // ... other configurations
    }
}
```

## UI Components

### 1. Book Grid

```kotlin
@Composable
fun BookGrid(
    state: DataState<List<Book>>,
    onBookSelected: (Book) -> Unit,
    onRetry: () -> Unit,
    onRefresh: () -> Unit
) {
    // Grid implementation with pull-to-refresh
}
```

### 2. Trending Tabs

```kotlin
@Composable
fun TrendingTabs(
    selectedCategory: TrendingCategory,
    selectedTimeRange: TrendingTimeRange,
    onCategorySelected: (TrendingCategory) -> Unit,
    onTimeRangeSelected: (TrendingTimeRange) -> Unit
) {
    // Tabs implementation
}
```

## Animations

### 1. Screen Transitions
```kotlin
AnimatedContent(
    targetState = selectedBook,
    transitionSpec = {
        (slideInHorizontally { width -> width } + fadeIn() with
                slideOutHorizontally { width -> -width } + fadeOut())
            .using(SizeTransform(clip = false))
    }
)
```

### 2. Content Animations
```kotlin
AnimatedVisibility(
    visible = isVisible,
    enter = fadeIn() + slideInVertically(),
    exit = fadeOut() + slideOutVertically()
)
```

## Theme Implementation

### 1. Color Scheme
```kotlin
val LightColors = lightColors(
    primary = Purple40,
    primaryVariant = PurpleGrey40,
    secondary = Pink40,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)
```

### 2. Typography
```kotlin
val BookpediaTypography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.ExtraBold,
        fontSize = 28.sp,
        letterSpacing = (-0.5).sp
    ),
    // ... other text styles
)
```

## API Integration

### 1. Data Models

#### Response Model
```kotlin
@Serializable
data class GoogleBooksResponse(
    val items: List<BookItem>,
    val totalItems: Int
)
```

#### Domain Model
```kotlin
data class Book(
    val id: String,
    val title: String,
    val authors: List<String>,
    val coverUrl: String,
    val description: String,
    // ... other properties
)
```

### 2. Data Mapping
```kotlin
fun BookItem.toDomainModel(): Book {
    return Book(
        id = id,
        title = volumeInfo.title,
        authors = volumeInfo.authors ?: emptyList(),
        // ... other mappings
    )
}
```

## Testing

### 1. Unit Tests
```kotlin
class GetTrendingBooksUseCaseTest {
    @Test
    fun `when repository returns success, use case should return success state`() {
        // Test implementation
    }
}
```

### 2. UI Tests
```kotlin
class BookGridTest {
    @Test
    fun testBookGridDisplaysCorrectly() {
        // UI test implementation
    }
}
```

## Performance Considerations

1. Image Loading
   - Coil for efficient image loading and caching
   - Proper image sizing and compression

2. Memory Management
   - Proper state handling
   - Efficient list rendering with keys
   - Memory leak prevention

3. Network Optimization
   - Proper error handling
   - Timeout management
   - Response caching

## Security

1. API Key Management
   - Secure storage of API keys
   - Request signing
   - HTTPS enforcement

2. Error Handling
   - Secure error messages
   - No sensitive data exposure
   - Proper exception handling

## Best Practices

1. Code Organization
   - Feature-based packaging
   - Clean Architecture principles
   - Single Responsibility Principle

2. UI/UX
   - Material Design guidelines
   - Proper error states
   - Loading indicators
   - Smooth animations

3. State Management
   - Unidirectional data flow
   - Immutable state
   - Proper state restoration

## Deployment

1. Requirements
   - Kotlin 1.9.0 or higher
   - Android Studio Arctic Fox or higher
   - Google Books API key

2. Setup Steps
   - Clone repository
   - Add API key
   - Sync Gradle
   - Build and run

## Troubleshooting

Common issues and solutions:
1. API Key Issues
2. Build Problems
3. Network Errors
4. UI Glitches

## Future Improvements

Potential enhancements:
1. Offline Support
2. User Authentication
3. Bookmarking
4. Social Sharing
5. Advanced Search 