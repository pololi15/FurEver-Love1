package fureverlove.ucb.pet

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ucb.domain.model.Mascota
import fureverlove.ucb.components.TopBarWithBack

// Colores unificados
val BlueMain = Color(0xFF439EF4)
val YellowAccent = Color(0xFFFFD600)
val BackgroundColor = Color(0xFFF8FAFC)
val MainText = Color(0xFF2D3748)

@Composable
fun PetDetailScreen(
    navController: NavController,
    viewModel: PetViewModel = hiltViewModel()
) {
    val mascotaState = viewModel.mascota.collectAsState()

    Scaffold(
        topBar = {
            TopBarWithBack(
                title = "Detalles de la Mascota",
                onBackClick = { navController.popBackStack() }
            )
        },
        containerColor = BackgroundColor
    ) { padding ->
        mascotaState.value?.let { mascota ->
            MascotaDetailContent(mascota, Modifier.padding(padding))
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = BlueMain)
            }
        }
    }
}

@Composable
fun MascotaDetailContent(mascota: Mascota, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Imagen principal a pantalla completa de ancho
        AsyncImage(
            model = mascota.fotoUrl,
            contentDescription = mascota.nombre,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.padding(24.dp)) {
            // Nombre y Especie
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = mascota.nombre,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MainText
                    )
                    Text(
                        text = mascota.especie.uppercase(),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BlueMain,
                        letterSpacing = 1.sp
                    )
                }
                
                // Badge de género
                val genderIcon = if (mascota.genero.lowercase() == "macho") "♂️" else "♀️"
                val genderColor = if (mascota.genero.lowercase() == "macho") Color(0xFFE3F2FD) else Color(0xFFFCE4EC)
                
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(genderColor)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text(text = "$genderIcon ${mascota.genero}", fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Tarjetas de información rápida
            Row(modifier = Modifier.fillMaxWidth()) {
                InfoCard(label = "Edad", value = mascota.edad, icon = Icons.Default.Info, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(16.dp))
                InfoCard(label = "Ubicación", value = mascota.ubicacion, icon = Icons.Default.LocationOn, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Descripción o detalles adicionales
            Text(
                text = "Sobre ${mascota.nombre}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MainText
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Esta adorable mascota está buscando un hogar lleno de amor. Es de categoría ${mascota.categoria} y se encuentra esperando por ti en ${mascota.ubicacion}.",
                fontSize = 16.sp,
                color = Color.Gray,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Botón de contacto (WhatsApp)
            Button(
                onClick = {
                    val phone = mascota.telefono
                    val message = "Hola, vi a ${mascota.nombre} en FurEver Love y me gustaría adoptarlo/a."
                    val url = "https://wa.me/${phone.replace("+", "").replace(" ", "")}?text=${Uri.encode(message)}"
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(url)
                    }

                    try {
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "No se pudo abrir WhatsApp", Toast.LENGTH_LONG).show()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BlueMain),
                elevation = ButtonDefaults.buttonElevation(8.dp)
            ) {
                Icon(Icons.Default.Phone, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    "ADOPTAR A ${mascota.nombre.uppercase()}",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun InfoCard(label: String, value: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = BlueMain, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = label, fontSize = 12.sp, color = Color.Gray)
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = MainText, maxLines = 1)
        }
    }
}

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    lineHeight: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    letterSpacing: androidx.compose.ui.unit.TextUnit = androidx.compose.ui.unit.TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        fontFamily = FontFamily.SansSerif,
        maxLines = maxLines,
        style = style
    )
}
