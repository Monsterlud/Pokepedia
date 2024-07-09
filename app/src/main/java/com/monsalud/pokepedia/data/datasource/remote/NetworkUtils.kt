package com.monsalud.pokepedia.data.datasource.remote

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkUtils {

    fun hasInternetConnection(connectivityManager: ConnectivityManager?): Boolean {
        val activeNetwork = connectivityManager?.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)
    }
}

object NetworkConstants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"
}