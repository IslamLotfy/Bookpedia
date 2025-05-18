package features.booklist.domain.usecase

data class BookUseCases(
    val getTrendingBooks: GetTrendingBooksUseCase,
    val searchBooks: SearchBooksUseCase
) 