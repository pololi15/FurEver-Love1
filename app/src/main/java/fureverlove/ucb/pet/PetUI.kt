package fureverlove.ucb.pet

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ucb.domain.model.Mascota
import fureverlove.ucb.components.TopBarWithBack
import fureverlove.ucb.home.FondoConPatitas

@Composable
fun PetDetailScreen(
    navController: NavController,
    viewModel: PetViewModel = hiltViewModel()
) {
    val mascotaState = viewModel.mascota.collectAsState()

    FondoConPatitas {
        Column(modifier = Modifier.fillMaxSize()) {
            TopBarWithBack(
                title = "Detalle Mascota",
                onBackClick = { navController.popBackStack() }
            )

            mascotaState.value?.let { mascota ->
                MascotaDetailContent(mascota)
            } ?: run {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
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
        val context = LocalContext.current
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

        Button(
            onClick = {
                val phone = mascota.telefono
                val message = "Hola, vengo de FurEver Love y quiero más información sobre la adopción."
                val url = "https://wa.me/${phone.replace("+", "")}?text=${message.replace(" ", "%20")}"
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(url)
                }

                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_LONG).show()
                }
            }
        ) {
            Text("Contactar")
        }
    }
}
