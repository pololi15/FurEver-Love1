
package fureverlove.ucb.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ucb.domain.model.Mascota
import fureverlove.ucb.home.model.Owner


@Composable
fun PetProfileScreen(pet: Mascota, owner: Owner) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = rememberAsyncImagePainter(pet.fotoUrl),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Nombre: ${pet.nombre}", style = MaterialTheme.typography.titleLarge)
        Text("Especie: ${pet.especie}")
        Text("Ubicación: ${pet.ubicacion}")

        Spacer(modifier = Modifier.height(24.dp))

        Text("Dueño: ${owner.name}", style = MaterialTheme.typography.titleMedium)
        Text("Teléfono: ${owner.phone}")
        Text("Email: ${owner.email}")
    }
}