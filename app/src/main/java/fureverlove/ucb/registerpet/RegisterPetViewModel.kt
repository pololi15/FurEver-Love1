package fureverlove.ucb.registerpet

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.ucb.domain.model.Mascota
import com.ucb.framework.firestore.FirestoreMascotaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class RegisterPetViewModel @Inject constructor(
    private val repository: FirestoreMascotaRepository
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun subirImagenYGuardar(uri: Uri, mascota: Mascota, context: Context, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                val storageRef = FirebaseStorage.getInstance().reference
                val imageRef = storageRef.child("mascotas/${UUID.randomUUID()}.jpg")

                val uploadTask = imageRef.putFile(uri).await()
                val downloadUrl = imageRef.downloadUrl.await()

                val mascotaConImagen = mascota.copy(fotoUrl = downloadUrl.toString())
                repository.agregarMascota(mascotaConImagen)

                _mensaje.value = "Mascota registrada con imagen con éxito"
                onSuccess()
            } catch (e: Exception) {
                _mensaje.value = "Error: ${e.message}"
                Toast.makeText(context, "Error al subir imagen: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}
