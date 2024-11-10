package com.example.world.core.domain

typealias RootError = Error
sealed interface Result<out D, out E: RootError> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out D, out E: RootError>(val error: E): Result<D, E>
    data object Loading : Result<Nothing, Nothing>
}