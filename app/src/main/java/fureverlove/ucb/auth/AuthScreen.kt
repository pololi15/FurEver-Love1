package fureverlove.ucb.auth

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccess: () -> Unit
) {
    val context = LocalContext.current
    var isLoginMode by remember { mutableStateOf(true) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    // Escucha cambios en el estado y muestra notificaciones
    LaunchedEffect(state) {
        when (state) {
            is AuthViewModel.AuthState.Success -> {
                val user = (state as AuthViewModel.AuthState.Success).user
                Toast.makeText(context, "Bienvenido ${user.email}", Toast.LENGTH_SHORT).show()
                viewModel.reset()
                onSuccess()
            }

            is AuthViewModel.AuthState.Error -> {
                val error = (state as AuthViewModel.AuthState.Error).message
                Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                viewModel.reset()
            }

            else -> {}
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isLoginMode) "Iniciar Sesión" else "Registrarse",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email.isNotBlank() && password.isNotBlank()) {
                        if (isLoginMode) {
                            viewModel.login(email, password)
                        } else {
                            viewModel.register(email, password)
                        }
                    } else {
                        Toast.makeText(context, "Rellena todos los campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isLoginMode) "Ingresar" else "Crear Cuenta")
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (isLoginMode) "¿No tienes cuenta? Regístrate" else "¿Ya tienes cuenta? Inicia sesión",
                modifier = Modifier
                    .clickable { isLoginMode = !isLoginMode }
                    .padding(8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            if (state is AuthViewModel.AuthState.Loading) {
                Spacer(modifier = Modifier.height(24.dp))
                CircularProgressIndicator()
            }
        }
    }
}
