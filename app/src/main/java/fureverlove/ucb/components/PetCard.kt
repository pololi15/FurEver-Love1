package fureverlove.ucb.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ucb.domain.model.Mascota

@Composable
fun PetCard(mascota: Mascota) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = mascota.fotoUrl,
                contentDescription = mascota.nombre,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(mascota.nombre, style = MaterialTheme.typography.titleMedium)
                Text("Edad: ${mascota.edad}")
                Text("Especie: ${mascota.especie}")
                Text("Ubicación: ${mascota.ubicacion}")
            }
        }
    }
}
@Composable
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
fun PetCardPreview() {
    PetCard(
        mascota = Mascota(
            nombre = "Luna",
            edad = "2 años",
            especie = "Perro",
            ubicacion = "La Paz",
            fotoUrl = "https://cdn2.thedogapi.com/images/B1Edfl9NX.jpg"
        )
    )
}
