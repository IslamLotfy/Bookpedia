package core.domain

import kotlinx.serialization.Serializable


sealed class DataState<out T> {
    data object Idle : DataState<Nothing>()

    data object Loading : DataState<Nothing>()

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val error : AppError) : DataState<Nothing>()
}

sealed class AppError {

    // 400 response code due to some business rules violation
    data class Business(val errorTag: ErrorTag) : AppError()

    // Any other error returned but the server
    data object Server : AppError()

    // Some edge cases from frontend handling
    data object Frontend : AppError()

    data class Exception(val exception: Throwable) : AppError()
}

enum class ErrorTag {
    DIFFERENT_ESTIMATED_COST_ERROR,
    REQUIRED_FIELD_ERROR,
    GENERIC
}
