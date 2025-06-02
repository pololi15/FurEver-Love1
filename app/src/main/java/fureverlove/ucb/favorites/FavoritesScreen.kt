package fureverlove.ucb.favorites

import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.LazyColumn
import fureverlove.ucb.home.model.PetCard
import androidx.compose.foundation.lazy.items
import com.ucb.domain.model.Mascota

@Composable
fun FavoritesScreen() {
    val favoritePets = listOf(
        Mascota(

            nombre = "Max",
            especie = "canino",
            //gendero = "macho",
            fotoUrl = "https://sp-ao.shortpixel.ai/client/to_auto,q_glossy,ret_img,w_800,h_420/https://hospitalveterinario.cr/wp-content/uploads/2019/02/gatos-adorables-1-1024x538.jpg",
            //categoria = "canes",
            ubicacion = "Calle 1, La Paz"
        ),
        Mascota(

            nombre = "Luna",
            especie = "felino",
            //gendero = "hembra",
            fotoUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRr2uP5h2rgkTWj7nVdNIBGefhNu88_eRFEjw&s",
            //categoria = "gatos",
            ubicacion = "Calle 2, Cochabamba"
        )
    )

    LazyColumn {
        items(favoritePets) { pet ->
            PetCard(pet = pet)
        }
    }
}