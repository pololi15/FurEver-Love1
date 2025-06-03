package fureverlove.ucb.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ucb.domain.model.Mascota
import fureverlove.ucb.components.PetCard
import fureverlove.ucb.components.TopBarWithBack
import fureverlove.ucb.home.FondoConPatitas
import fureverlove.ucb.navigation.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    navController: NavController,
    onPetClick: (String) -> Unit
) {
    val favoritePets = listOf(
        Mascota(
            id = "1",
            nombre = "Exterminador",
            edad = "2 meses",
            especie = "Felino",
            genero = "macho",
            fotoUrl = "https://sp-ao.shortpixel.ai/client/to_auto,q_glossy,ret_img,w_800,h_420/https://hospitalveterinario.cr/wp-content/uploads/2019/02/gatos-adorables-1-1024x538.jpg",
            categoria = "Gatos",
            ubicacion = "Calle 1, La Paz"
        ),
        Mascota(
            id = "2",
            nombre = "Luna",
            edad = "3 años",
            especie = "felino",
            genero = "hembra",
            fotoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRr2uP5h2rgkTWj7nVdNIBGefhNu88_eRFEjw&s",
            categoria = "gatos",
            ubicacion = "Robore, Santa Cruz"
        ),

        Mascota(
         id = "3",
        nombre = "Botas",
        edad = "6 meses",
        especie = "canino",
        genero = "Macho",
        fotoUrl = "https://www.mascotastravel.com/images/blog/gl-15.jpg",
        categoria = "perros",
        ubicacion = "Colcapirua"
        ),


        Mascota(
            id = "4",
            nombre = "Nisha",
            edad = "5 años",
            especie = "Felino",
            genero = "Hembra",
            fotoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSfp6XGmUdj3ERkSaGO0ihGUFOR5oiGNGtuog&s",
            categoria = "Gato",
            ubicacion = "Vinto"
        ),


    )

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
                            onClick = { onPetClick(mascota.id) }
                        )
                    }
                }
            }
        }
    }
}
