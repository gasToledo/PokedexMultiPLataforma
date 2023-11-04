package com.example.pokedexmultip.android.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedexmultip.network.PokedexRepository
import com.example.pokedexmultip.repositories.PokedexDBRepository
import kotlinx.coroutines.launch

class PokedexViewModel(
    pokedexRepository: PokedexRepository,
    pokedexDBRepository: PokedexDBRepository,
    context: Context
) : ViewModel() {

    private val _pokedex = MutableLiveData<PokedexScreenState>(PokedexScreenState.Loading)

    val pokedex: LiveData<PokedexScreenState> = _pokedex

    var screenUbication by mutableStateOf("pokedex_screen")


    init {
        viewModelScope.launch {

            if (checkNetwork(context)) {

                _pokedex.value =
                    PokedexScreenState.ShowPokedex(pokedexRepository.getPokedex().responses)

                if (pokedexDBRepository.getAllPokemon().isEmpty()) {

                    pokedexRepository.getPokedex().responses.forEach {
                        pokedexDBRepository.addPokemon(it)
                    }
                }

            } else {

                if (pokedexDBRepository.getAllPokemon().isNotEmpty()) {

                    _pokedex.value =
                        PokedexScreenState.ShowPokedex(pokedexDBRepository.getAllPokemon())
                } else {

                    _pokedex.value = PokedexScreenState.Error
                }
            }
        }
    }


    private fun checkNetwork(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val networkinfo =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return networkinfo.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkinfo.hasTransport(
            NetworkCapabilities.TRANSPORT_CELLULAR
        )
    }
}
