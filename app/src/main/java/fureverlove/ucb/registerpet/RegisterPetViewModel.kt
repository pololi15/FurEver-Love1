package fureverlove.ucb.registerpet

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.ucb.data.mascota.IMascotaRepository
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
   // private val repository: IMascotaRepository//FirestoreMascotaRepository
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
    // esto es interesante, el primer state tiene que ser mutable y privado
    // y el segundo state es inmutable y solo se puede leer para la ui

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun subirImagenYGuardar(uri: Uri, mascota: Mascota, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _estado.value = RegisterState.Loading

                val storageRef = FirebaseStorage.getInstance().reference
                val imageRef = storageRef.child("mascotas/${UUID.randomUUID()}.jpg")

                imageRef.putFile(uri).await()
                val downloadUrl = imageRef.downloadUrl.await()

                val mascotaConImagen = mascota.copy(fotoUrl = downloadUrl.toString())
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
