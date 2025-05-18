package core.domain

import kotlinx.serialization.Serializable


sealed class DataState<out T> {
    data object Idle : DataState<Nothing>()

    data object Loading : DataState<Nothing>()

    data class Success<out T>(val data: T) : DataState<T>()

    data class Error(val error : AppError) : DataState<Nothing>()
}

sealed class AppError(open val errorMessage:String) {

    // 400 response code due to some business rules violation
    data class Business(override val errorMessage:String) : AppError(errorMessage)

    // Any other error returned but the server
    data class Server(override val errorMessage:String) : AppError(errorMessage)

    // Some edge cases from frontend handling
    data class Frontend(override val errorMessage:String) : AppError(errorMessage)

    data class Exception(override val errorMessage:String) : AppError(errorMessage)
}