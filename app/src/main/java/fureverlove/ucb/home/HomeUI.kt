package fureverlove.ucb.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import fureverlove.ucb.components.CiudadYZonaHoraria
import fureverlove.ucb.components.PetCard
import fureverlove.ucb.navigation.BottomNavigationBar

// Paleta de colores consistente
val BlueMain = Color(0xFF439EF4)
val YellowAccent = Color(0xFFFFD600)
val BackgroundColor = Color(0xFFF8FAFC)
val MainText = Color(0xFF2D3748)
val GrayLight = Color(0xFFF1F5F9)

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
            title = { Text("Permiso requerido", fontWeight = FontWeight.Bold) },
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
                        text = "FurEver Love",
                        color = BlueMain,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                actions = {
                    IconButton(onClick = {
                        FirebaseAuth.getInstance().signOut()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar sesión", tint = MainText)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = BackgroundColor
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Ubicación y Ciudad
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Ubicación",
                        tint = BlueMain,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    CiudadYZonaHoraria(
                        modifier = Modifier.weight(1f),
                        onError = { error -> Log.e("HomeUI", "Error ubicación: $error") }
                    )
                }
            }

            // Banner mejorado
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .clip(RoundedCornerShape(24.dp))
                        .background(BlueMain)
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            "¡Una patita amiga\nte está esperando!",
                            fontSize = 20.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            lineHeight = 28.sp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onAddPet,
                            colors = ButtonDefaults.buttonColors(containerColor = YellowAccent),
                            shape = RoundedCornerShape(12.dp),
                            elevation = ButtonDefaults.buttonElevation(4.dp)
                        ) {
                            Text("REGISTRAR MASCOTA", color = Color.Black, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Sección de Categorías
            item {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        "Categorías",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MainText
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        CategoryItem(
                            label = "Todos",
                            isSelected = selectedCategory == "todos",
                            onClick = { selectedCategory = "todos" },
                            modifier = Modifier.weight(1f)
                        )
                        CategoryItem(
                            label = "Canes",
                            isSelected = selectedCategory == "perro",
                            onClick = { 
                                selectedCategory = "perro"
                                onCategoryClick("canes")
                            },
                            modifier = Modifier.weight(1f)
                        )
                        CategoryItem(
                            label = "Gatos",
                            isSelected = selectedCategory == "gato",
                            onClick = { 
                                selectedCategory = "gato"
                                onCategoryClick("gatos")
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Título de lista
            item {
                Text(
                    "Mascotas cerca de ti",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MainText
                )
            }

            // Filtrar y Listar mascotas
            val filteredMascotas = when (selectedCategory) {
                "perro" -> mascotas.filter { it.especie.trim().contains("perro", ignoreCase = true) || it.especie.trim().contains("can", ignoreCase = true) }
                "gato" -> mascotas.filter { it.especie.trim().contains("gato", ignoreCase = true) || it.especie.trim().contains("felino", ignoreCase = true) }
                else -> mascotas
            }

            if (filteredMascotas.isEmpty()) {
                item {
                    Box(modifier = Modifier.fillMaxWidth().padding(40.dp), contentAlignment = Alignment.Center) {
                        Text("No se encontraron mascotas en esta categoría", color = Color.Gray)
                    }
                }
            } else {
                items(filteredMascotas) { mascota ->
                    val isFavorite = favoritos.contains(mascota.id)
                    val onToggleFavorite = { viewModel.toggleFavorite(mascota.id) }

                    Box(modifier = Modifier.padding(horizontal = 20.dp)) {
                        PetCard(
                            mascota = mascota,
                            isFavorite = isFavorite,
                            onFavoriteClick = onToggleFavorite,
                            onClick = { onPetClick(mascota.id) }
                        )
                    }
                }
            }
            
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
fun CategoryItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = if (isSelected) BlueMain else Color.White
    val textColor = if (isSelected) Color.White else MainText
    
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            fontSize = 14.sp
        )
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
    style: androidx.compose.ui.text.TextStyle = LocalTextStyle.current
) {
    androidx.compose.material3.Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        lineHeight = lineHeight,
        fontFamily = FontFamily.SansSerif,
        style = style
    )
}
