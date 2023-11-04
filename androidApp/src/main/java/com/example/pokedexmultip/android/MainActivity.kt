package com.example.pokedexmultip.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.pokedexmultip.DatabaseDriverFactory
import com.example.pokedexmultip.android.domain.navigation.PokedexNavigation
import com.example.pokedexmultip.android.ui.PokedexViewModel
import com.example.pokedexmultip.network.PokedexRepository
import com.example.pokedexmultip.repositories.PokedexDBRepository

class MainActivity : ComponentActivity() {

    private lateinit var pokedexRepository: PokedexRepository
    private lateinit var pokedexViewModel: PokedexViewModel
    private lateinit var pokedexDBRepository: PokedexDBRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface {
                PokedexTheme(darkTheme = false) {
                    pokedexRepository = PokedexRepository()
                    pokedexDBRepository =
                        PokedexDBRepository(DatabaseDriverFactory(context = applicationContext))
                    pokedexViewModel =
                        PokedexViewModel(pokedexRepository, pokedexDBRepository, this)

                    PokedexNavigation(pokedexViewModel = pokedexViewModel)
                }
            }
        }
    }
}
