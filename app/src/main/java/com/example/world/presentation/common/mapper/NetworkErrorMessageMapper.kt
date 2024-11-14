package com.example.world.presentation.common.mapper

import com.example.world.R
import com.example.world.domain.model.DataError
import com.example.world.presentation.common.utils.UiMessage


fun DataError.Network.toUiNetworkErrorMessage(): UiMessage {
    return when(this){
        DataError.Network.NO_INTERNET -> UiMessage.StringResource(R.string.no_internet)
        DataError.Network.SERVER_ERROR -> UiMessage.StringResource(R.string.server_error)
        DataError.Network.ACCESS_DENIED -> UiMessage.StringResource(R.string.access_denied)
        DataError.Network.UNABLE_TO_PROCESS -> UiMessage.StringResource(R.string.unable_to_process)
        DataError.Network.UNAUTHORIZED_USER -> UiMessage.StringResource(R.string.unauthorised_user)
        DataError.Network.UNKNOWN -> UiMessage.StringResource(R.string.unknown_error)
    }
}