package com.example.world.core.domain

sealed interface DataError: Error {
    enum class Network: DataError {
        NO_INTERNET,
        SERVER_ERROR,
        UNAUTHORIZED_USER,
        UNABLE_TO_PROCESS,
        ACCESS_DENIED,
        UNKNOWN
    }
    enum class Local: DataError {
        DISK_FULL
    }
}