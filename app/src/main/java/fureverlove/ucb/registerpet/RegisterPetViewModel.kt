package fureverlove.ucb.registerpet

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.auth.FirebaseAuth
import com.ucb.domain.model.Mascota
import com.ucb.usecases.SavePet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterPetViewModel @Inject constructor(
    private val savePet: SavePet
    ) : ViewModel() {
    sealed class RegisterState {
        object Idle : RegisterState()
        object Loading : RegisterState()
        object Success : RegisterState()
        data class Error(val mensaje: String) : RegisterState()
    }

    private val _estado = MutableStateFlow<RegisterState>(RegisterState.Idle)
    val estado: StateFlow<RegisterState> = _estado

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun subirImagenYGuardar(uri: Uri, mascota: Mascota, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _estado.value = RegisterState.Loading

                val userId = FirebaseAuth.getInstance().currentUser?.uid
                    ?: throw SecurityException("Debes iniciar sesión para publicar una mascota")
                val storageRef = FirebaseStorage.getInstance().reference
                val imageRef = storageRef.child("mascotas/$userId/${UUID.randomUUID()}.jpg")

                imageRef.putFile(uri).await()
                val downloadUrl = imageRef.downloadUrl.await()

                val mascotaConImagen = mascota.copy(
                    fotoUrl = downloadUrl.toString(),
                    creadorId = userId
                )
                savePet(mascotaConImagen)

                _estado.value = RegisterState.Success
                _mensaje.value = "Mascota registrada con éxito"
                onSuccess()
            } catch (e: Exception) {
                _estado.value = RegisterState.Error(e.message ?: "Error desconocido")
                _mensaje.value = "Error: ${e.message}"
            }
        }
    }
}
