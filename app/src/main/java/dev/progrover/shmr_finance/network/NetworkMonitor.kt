package dev.progrover.shmr_finance.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest

class NetworkMonitor(
    private val context: Context,
    private val onNetworkAvailable: () -> Unit
) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private var isCurrentlyConnected = false
    private var initialNetwork: Network? = null

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            if (initialNetwork != null && initialNetwork == network) {
                initialNetwork = null
                return
            }

            if (!isCurrentlyConnected) {
                isCurrentlyConnected = true
                onNetworkAvailable()
            }
        }

        override fun onLost(network: Network) {
            isCurrentlyConnected = false
        }
    }

    fun start() {
        initialNetwork = connectivityManager.activeNetwork
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(request, networkCallback)
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}