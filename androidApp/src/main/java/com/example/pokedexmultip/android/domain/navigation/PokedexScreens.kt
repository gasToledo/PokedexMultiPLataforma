package com.example.pokedexmultip.android.domain.navigation

sealed class PokedexScreens(val route: String) {

    object PokedexScreen : PokedexScreens("pokedex_screen")
    object PokedexStart : PokedexScreens("pokedex_start")
}