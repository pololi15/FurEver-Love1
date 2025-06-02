package fureverlove.ucb.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import fureverlove.ucb.components.PetCard
import fureverlove.ucb.navigation.BottomNavigationBar

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
    var selectedItem by remember { mutableStateOf("home") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mascotas en adopción") },
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
            // Encabezado
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Cochabamba, Bolivia", fontSize = 14.sp)
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
                            Text("DAR EN ADOPCIÓN")
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
                        onClick = { onCategoryClick("canes") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF50E3C2)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("CANES")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onCategoryClick("gatos") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("GATOS")
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Lista de mascotas
            item {
                Text(
                    "Mascotas en adopción",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(mascotas) { mascota ->
                PetCard(
                    mascota = mascota,
                    onClick = { onPetClick(mascota.id) }
                )
            }
        }
    }
}
