package com.thomas200593.mini_retail_app.core.design_system.network_monitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET
import android.net.NetworkRequest.Builder
import androidx.compose.ui.util.trace
import androidx.core.content.getSystemService
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatcher
import com.thomas200593.mini_retail_app.core.design_system.coroutine_dispatchers.Dispatchers.Dispatchers.IO
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface NetworkMonitor {
    val isNetworkOnline: Flow<Boolean>
}

internal class NetworkMonitorImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher
) : NetworkMonitor {
    override val isNetworkOnline: Flow<Boolean> = callbackFlow {
        trace("NetworkMonitor.callbackFlow"){
            val connectivityManager = context.getSystemService<ConnectivityManager>()
            if(connectivityManager == null){
                channel.trySend(false)
                channel.close()
                return@callbackFlow
            }
            val callback = object : NetworkCallback(){
                private val networks = mutableSetOf<Network>()
                override fun onAvailable(network: Network) {
                    networks += network
                    channel.trySend(true)
                }
                override fun onLost(network: Network) {
                    networks -= network
                    channel.trySend(networks.isNotEmpty())
                }
            }
            trace("NetworkMonitor.registerNetworkCallback"){
                val request = Builder().addCapability(NET_CAPABILITY_INTERNET).build()
                connectivityManager.registerNetworkCallback(request, callback)
            }
            channel.trySend(connectivityManager.isCurrentlyConnected())
            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }
    }.flowOn(ioDispatcher).conflate()

    private fun ConnectivityManager.isCurrentlyConnected() = activeNetwork
        ?.let(::getNetworkCapabilities)
        ?.hasCapability(NET_CAPABILITY_INTERNET) ?: false
}