package fureverlove.ucb.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import fureverlove.ucb.components.CiudadYZonaHoraria
import fureverlove.ucb.navigation.BottomNavigationBar
import com.ucb.domain.model.Mascota
import fureverlove.ucb.pet.PetCat
import fureverlove.ucb.pet.PetDog

private const val LOCATION_PERMISSION_REQUEST_CODE = 1001

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeUI(
    viewModel: HomeViewModel = hiltViewModel(),
    onLogout: () -> Unit,
    onAddPet: () -> Unit,
    navController: NavController,
    onPetClick: (String) -> Unit,
    onGoToFavorites: () -> Unit = {},
    onCategoryClick: (String) -> Unit = {}
) {
    val mascotas by viewModel.mascotas.collectAsState()
    val favoritos by viewModel.favoritos.collectAsState()
    var selectedCategory by remember { mutableStateOf("todos") }
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            showPermissionDialog = true
        }
    }

    LaunchedEffect(Unit) {
        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasPermission) {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Permiso requerido") },
            text = { Text("Para mostrar mascotas cerca de ti, necesitamos acceso a tu ubicación") },
            confirmButton = {
                Button(onClick = {
                    showPermissionDialog = false
                    (context as? Activity)?.let {
                        ActivityCompat.requestPermissions(
                            it,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            LOCATION_PERMISSION_REQUEST_CODE
                        )
                    }
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mascotas en adopción",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(8.dp)
        ) {
            // Ciudad y zona horaria
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ubicación",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        CiudadYZonaHoraria(
                            modifier = Modifier.weight(1f),
                            onError = { error ->
                                Log.e("HomeUI", "Error ubicación: $error")
                            }
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(8.dp)) }

            // Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF439EF4))
                        .padding(16.dp)
                ) {
                    Column {
                        Text(
                            "Una patita amiga\nnecesita de ti.",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = onAddPet,
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600))
                        ) {
                            Text("REGISTRAR MASCOTA")
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Categorías
            item {
                Text(
                    "Categorías",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            item {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            selectedCategory = "perro"
                            onCategoryClick("canes")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF50E3C2)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("CANES")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = {
                            selectedCategory = "gato"
                            onCategoryClick("gatos")
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("GATOS")
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Filtrar por especie
            val filteredMascotas = when (selectedCategory) {
                "perro" -> mascotas.filter { it.especie.equals("perro", ignoreCase = true) }
                "gato" -> mascotas.filter { it.especie.equals("gato", ignoreCase = true) }
                else -> mascotas
            }

            // Mostrar mascotas
            items(filteredMascotas) { mascota ->
                val isFavorite = favoritos.contains(mascota.id)
                val onToggleFavorite = { viewModel.toggleFavorite(mascota.id) }

                when {
                    mascota.especie.equals("perro", ignoreCase = true) -> {
                        PetDog(
                            mascota = mascota,
                            isFavorite = isFavorite,
                            onFavoriteClick = onToggleFavorite,
                            onClick = { onPetClick(mascota.id) }
                        )
                    }
                    mascota.especie.equals("gato", ignoreCase = true) -> {
                        PetCat(
                            mascota = mascota,
                            isFavorite = isFavorite,
                            onFavoriteClick = onToggleFavorite,
                            onClick = { onPetClick(mascota.id) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
