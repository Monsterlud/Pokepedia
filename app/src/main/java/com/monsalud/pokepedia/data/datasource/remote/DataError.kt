package com.monsalud.pokepedia.data.datasource.remote

sealed class DataError : Throwable() {

    data class Network(val errorType: Throwable) : DataError()
    data class Unknown(val errorType: Throwable) : DataError()
}