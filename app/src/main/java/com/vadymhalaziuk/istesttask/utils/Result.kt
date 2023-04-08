package com.vadymhalaziuk.istesttask.utils

sealed class Result<out T, out E>(
    private val _value: T?,
    private val _error: E?
) {
    data class Success<T>(
        val valueParam: T
    ) : Result<T, Nothing>(valueParam, null)

    data class Error<Nothing, E>(
        val errorParam: E
    ) : Result<Nothing, E>(null, errorParam)

    val value: T
        get() = requireNotNull(_value)

    val error: E
        get() = requireNotNull(_error)

    val isSuccess: Boolean
        get() = _value != null

    val isError
        get() = _error != null

    inline fun ifSuccess(invoke: () -> Unit) {
        if (isSuccess) {
            invoke()
        }
    }

    inline fun ifError(invoke: () -> Unit) {
        if (isError) {
            invoke()
        }
    }


    companion object {
        fun <T> safeResult(invoke: () -> T): Result<T, ErrorType> {
            return kotlin.runCatching {
                Result.Success(
                    invoke()
                )
            }.getOrElse { Result.Error(ErrorType.Unknown) }
        }
    }

    sealed class ErrorType {

        object Internet : ErrorType()
        object Unknown : ErrorType()
    }

}
