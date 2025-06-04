package fureverlove.ucb.pet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ucb.domain.model.Mascota



@Composable
fun PetDog(
    mascota: Mascota,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    if (mascota.especie.lowercase() == "perro") {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick() },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            )
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

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = mascota.nombre,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {
                            InfoItem("Edad", mascota.edad)
                            InfoItem("Especie", mascota.especie)
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            InfoItem("Ubicación", mascota.ubicacion)
                            InfoItem("Género", mascota.genero)
                        }
                    }
                }
            }
        }
    }

}
@Composable
private fun InfoItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}