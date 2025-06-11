package fureverlove.ucb.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fureverlove.ucb.components.PetCard
import fureverlove.ucb.components.TopBarWithBack
import fureverlove.ucb.home.FondoConPatitas
import fureverlove.ucb.home.HomeViewModel
import fureverlove.ucb.navigation.BottomNavigationBar

@Composable
fun FavoritesScreen(
    navController: NavController,
    onPetClick: (String) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val allPets by homeViewModel.mascotas.collectAsState()
    val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()

    val favoritePets = allPets.filter { pet ->
        favoriteIds.contains(pet.id)
    }
    FondoConPatitas {
        Scaffold(
            topBar = {
                TopBarWithBack(
                    title = "Mascotas Favoritas",
                    onBackClick = { navController.popBackStack() }
                )
            },
            bottomBar = {
                BottomNavigationBar(navController)
            }
        ) { padding ->
            if (favoritePets.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay mascotas favoritas.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(8.dp)
                ) {
                    items(favoritePets) { mascota ->
                        PetCard(
                            mascota = mascota,
                            isFavorite = true,
                            onClick = { onPetClick(mascota.id) },
                            onFavoriteClick = {
                                favoriteViewModel.toggleFavorite(mascota.id)
                            }
                        )
                    }
                }
            }
        }
    }
}
