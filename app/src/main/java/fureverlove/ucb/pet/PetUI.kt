package fureverlove.ucb.pet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ucb.domain.model.Mascota

@Composable
fun PetDetailScreen(
    viewModel: PetViewModel = hiltViewModel()
) {
    val mascotaState = viewModel.mascota.collectAsState()

    mascotaState.value?.let { mascota ->
        MascotaDetailContent(mascota)
    } ?: run {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
@Composable
fun MascotaDetailContent(mascota: Mascota) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = mascota.fotoUrl,
            contentDescription = mascota.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Nombre: ${mascota.nombre}", style = MaterialTheme.typography.headlineSmall)
        Text(text = "Edad: ${mascota.edad}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Especie: ${mascota.especie}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Ubicación: ${mascota.ubicacion}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "Género: ${mascota.genero}", style = MaterialTheme.typography.bodyLarge)
    }
}

