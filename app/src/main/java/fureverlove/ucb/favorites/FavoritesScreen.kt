package fureverlove.ucb.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import fureverlove.ucb.components.PetCard
import fureverlove.ucb.components.TopBarWithBack
import fureverlove.ucb.navigation.BottomNavigationBar
import fureverlove.ucb.home.FondoConPatitas
import com.ucb.domain.model.Mascota

@Composable
fun FavoritesScreen(
    navController: NavController,
    onPetClick: (String) -> Unit,
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoritos by favoriteViewModel.favoritePets.collectAsState()

    LaunchedEffect(Unit) {
        favoriteViewModel.loadFavorites()
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
            if (favoritos.isEmpty()) {
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
                    items(favoritos) { mascota ->
                        PetCard(
                            mascota = mascota,
                            isFavorite = true,
                            onClick = { onPetClick(mascota.id) },
                            onFavoriteClick = {
                                favoriteViewModel.toggleFavorite(mascota)
                            }
                        )
                    }
                }
            }
        }
    }
}

