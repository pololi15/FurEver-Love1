package fureverlove.ucb.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    onCategoryClick: (String) -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // 1. Encabezado con botón de logout
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Cochabamba, Bolivia", fontSize = 14.sp)
                Button(onClick = {
                    FirebaseAuth.getInstance().signOut()
                    onLogout()
                }) {
                    Text("Cerrar sesión")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 2. Banner promocional
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
                        onClick = { /* Navegar a pantalla de adopción */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFD600))
                    ) {
                        Text("DAR EN ADOPCIÓN")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // 3. Sección de categorías (equivalente a HomeUI)
            Text("Categorías",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { onCategoryClick("canes") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF50E3C2)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("CANES")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = { onCategoryClick("gatos") },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFFA726)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("GATOS")
                }
            }
        }
    }
}
@Composable
fun HomeUI(onCategoryClick: (String) -> Unit) {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Categorías", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))

        Row {
            Button(onClick = { onCategoryClick("canes") }, modifier = Modifier.weight(1f)) {
                Text("CANES")
            }
            Spacer(Modifier.width(8.dp))
            Button(onClick = { onCategoryClick("gatos") }, modifier = Modifier.weight(1f)) {
                Text("GATOS")
            }
        }
    }
}
