package com.example.pokedexmultip.repositories

import com.example.pokedex.PokedexDB
import com.example.pokedexmultip.DatabaseDriverFactory
import com.example.pokedexmultiplatform.serializable.PokedexResponse

class PokedexDBRepository(databaseDriverFactory: DatabaseDriverFactory) {

    private val database = PokedexDB(databaseDriverFactory.createDriver())
    private val queries = database.pokedexQueries

    fun clearDatabase() {
        queries.transaction {
            queries.deleteAllPokemon()
        }
    }

    fun getAllPokemon(): List<PokedexResponse> {
        return queries.selectAllPokemon(::mapPokemonSelecting).executeAsList()
    }

    private fun mapPokemonSelecting(
        mapName: String,
        mapUrl: String
    ): PokedexResponse {
        return PokedexResponse(name = mapName, url = mapUrl)
    }

    fun addPokemon(pokemon: PokedexResponse) {
        queries.insertPokemon(name = pokemon.name, url = pokemon.url)
    }

}