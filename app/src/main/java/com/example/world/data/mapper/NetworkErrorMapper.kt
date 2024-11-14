package com.example.world.data.mapper

import com.example.world.domain.model.DataError
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toNetworkError(): DataError.Network{
    return when(this){
        is HttpException -> {
            when(this.code()){
                401 -> DataError.Network.UNAUTHORIZED_USER
                400 -> DataError.Network.UNABLE_TO_PROCESS
                403 -> DataError.Network.ACCESS_DENIED
                else -> DataError.Network.UNKNOWN
            }
        }

        is IOException -> DataError.Network.NO_INTERNET
        else -> DataError.Network.UNKNOWN
    }
}